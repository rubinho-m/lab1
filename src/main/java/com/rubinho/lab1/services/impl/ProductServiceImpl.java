package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.mappers.ProductMapper;
import com.rubinho.lab1.model.Coordinates;
import com.rubinho.lab1.model.Product;
import com.rubinho.lab1.model.Role;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.CoordinatesRepository;
import com.rubinho.lab1.repository.ProductFilter;
import com.rubinho.lab1.repository.ProductRepository;
import com.rubinho.lab1.services.ProductService;
import com.rubinho.lab1.services.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final ProductMapper productMapper;
    private final ProductSpecificationService productSpecificationService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CoordinatesRepository coordinatesRepository,
                              ProductMapper productMapper,
                              ProductSpecificationService productSpecificationService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productSpecificationService = productSpecificationService;
        this.coordinatesRepository = coordinatesRepository;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductDto createProduct(ProductDto productDto, User user) {
        final Product product = productMapper.toEntity(productDto);
        product.setUser(user);
        try {
            return productMapper.toDto(productRepository.save(product));
        } catch (DataIntegrityViolationException e) {
            final Coordinates coordinates = product.getCoordinates();
            if (coordinatesRepository.existsByXAndY(coordinates.getX(), coordinates.getY())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coordinates already exist");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location already exists");
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductDto createErrorProduct(ProductDto productDto, User user) {
        final Product product = productMapper.toEntity(productDto);
        product.setUser(user);
        try {
            productRepository.save(product);
            throw new RuntimeException("TEST EXCEPTION");
        } catch (DataIntegrityViolationException e) {
            final Coordinates coordinates = product.getCoordinates();
            if (coordinatesRepository.existsByXAndY(coordinates.getX(), coordinates.getY())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coordinates already exist");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location already exists");
        }
    }

    @Override
    @Transactional
    public List<ProductDto> createProducts(List<ProductDto> productsDto, User user) {
        return List.of();
    }

    @Override
    public ProductDto getProductById(long id) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found by this id"));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts(Pageable paging, ProductFilter productFilter) {
        final Specification<Product> spec = productSpecificationService.filterBy(productFilter);
        final Page<Product> productPage = productRepository.findAll(spec, paging);
        return productPage
                .stream()
                .map(productMapper::toDto)
                .peek(productDto -> productDto.setTotalPages(productPage.getTotalPages()))
                .toList();
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        final Product product = productMapper.toEntity(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void checkUser(long id, User user) {
        final Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found by this id"));
        if (product.getUser().equals(user) || user.getRole().equals(Role.ADMIN)) {
            return;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not user of this product");
    }

    @Override
    public void removeByRating(Double rating, User user) {
        final List<Product> products = productRepository.findAllByUserAndRating(user, rating);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found");
        }
        productRepository.delete(products.get(0));
    }

    @Override
    public Double getSumRating(User user) {
        final List<Product> products = productRepository.findAllByUser(user);
        return products
                .stream()
                .mapToDouble(Product::getRating)
                .sum();
    }

    @Override
    public List<ProductDto> getAllProductsBySubstring(String substring, Pageable paging, ProductFilter productFilter) {
        final Specification<Product> spec = productSpecificationService.filterBy(productFilter);
        final Page<Product> productPage = productRepository.findAll(spec, paging);
        return productPage
                .stream()
                .filter(product -> product.getName().contains(substring))
                .map(productMapper::toDto)
                .peek(productDto -> productDto.setTotalPages(productPage.getTotalPages()))
                .toList();
    }

    @Override
    @Transactional
    public void decreasePriceOnPercent(Integer percent, User user) {
        final List<Product> products = productRepository.findAllByUser(user);
        for (Product product : products) {
            final int newPrice = (int) Math.round(product.getPrice() * (1 - (double) percent / (double) 100));
            product.setPrice(newPrice);
            productRepository.save(product);
        }
    }
}
