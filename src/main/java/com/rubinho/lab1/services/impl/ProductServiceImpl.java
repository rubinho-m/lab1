package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.mappers.ProductMapper;
import com.rubinho.lab1.model.Product;
import com.rubinho.lab1.model.Role;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.ProductFilter;
import com.rubinho.lab1.repository.ProductRepository;
import com.rubinho.lab1.services.ProductService;
import com.rubinho.lab1.services.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductSpecificationService productSpecificationService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper,
                              ProductSpecificationService productSpecificationService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productSpecificationService = productSpecificationService;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, User user) {
        final Product product = productMapper.toEntity(productDto);
        product.setUser(user);
        return productMapper.toDto(productRepository.save(product));
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
        return productRepository.findAll(spec, paging)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
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
        final Product product = productRepository.findByUserAndRating(user, rating)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found by this id"));
        productRepository.delete(product);
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
        return productRepository.findAll(spec, paging)
                .stream()
                .filter(product -> product.getName().contains(substring))
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public void decreasePriceOnPercent(Integer percent, User user) {
        final List<Product> products = productRepository.findAllByUser(user);
        for (Product product : products) {
            product.setPrice(product.getPrice() * (1 - percent / 100));
            productRepository.save(product);
        }
    }
}
