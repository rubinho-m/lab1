package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.mappers.ProductMapper;
import com.rubinho.lab1.model.Organization;
import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.model.Product;
import com.rubinho.lab1.model.Role;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.ProductRepository;
import com.rubinho.lab1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, Person owner) {
        final Product product = productMapper.toEntity(productDto);
        product.setOwner(owner);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto getProductById(long id) {
        final Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No organization found by this id"));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts(Pageable paging) {
        return productRepository.findAll(paging)
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
    public void checkOwner(long id, User owner) {
        final Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No organization found by this id"));
        if (product.getOwner().equals(owner.getPerson()) || owner.getRole().equals(Role.ADMIN)) {
            return;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not owner of this product");
    }

    @Override
    public void removeByRating(Double rating, Person owner) {
        final Product product = productRepository.findByOwnerAndRating(owner, rating).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found by this id"));
        productRepository.delete(product);
    }

    @Override
    public Double getSumRating(Person owner) {
        final List<Product> products = productRepository.findAllByOwner(owner);
        return products.stream().mapToDouble(Product::getRating).sum();
    }

    @Override
    public List<ProductDto> getAllProductsBySubstring(String substring, Pageable paging, Person owner) {
        final List<Product> products = productRepository.findAllByOwner(owner, paging);
        return products
                .stream()
                .filter(product -> product.getName().contains(substring))
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getAllProductsByManufacturer(Organization organization, Pageable paging, Person owner) {
        final List<Product> products = productRepository.findAllByOwner(owner, paging);
        return products
                .stream()
                .filter(product -> product.getManufacturer().getId() == organization.getId())
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public void decreasePriceOnPercent(Integer percent, Person owner) {
        final List<Product> products = productRepository.findAllByOwner(owner);
        for (Product product : products) {
            product.setPrice(product.getPrice() * (1 - percent / 100));
            productRepository.save(product);
        }
    }
}
