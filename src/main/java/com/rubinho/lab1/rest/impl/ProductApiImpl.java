package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.mappers.OrganizationMapper;
import com.rubinho.lab1.model.Organization;
import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.rest.ProductApi;
import com.rubinho.lab1.services.OrganizationService;
import com.rubinho.lab1.services.UserService;
import com.rubinho.lab1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductApiImpl implements ProductApi {
    private final ProductService productService;
    private final UserService userService;
    private final OrganizationService organizationService;
    private final OrganizationMapper organizationMapper;

    @Autowired
    public ProductApiImpl(ProductService productService,
                          UserService userService,
                          OrganizationService organizationService,
                          OrganizationMapper organizationMapper) {
        this.productService = productService;
        this.userService = userService;
        this.organizationService = organizationService;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto, String token) {
        final Person owner = userService.getUserByToken(getToken(token)).getPerson();
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto, owner));
    }

    @Override
    public ResponseEntity<ProductDto> getProductById(Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getAllProducts(int page, int limit) {
        return ResponseEntity.ok(productService.getAllProducts(PageRequest.of(page, limit)));
    }

    @Override
    public ResponseEntity<ProductDto> updateProduct(ProductDto productDto, String token) {
        productService.checkOwner(productDto.getId(), userService.getUserByToken(getToken(token)));
        return ResponseEntity.accepted().body(productService.updateProduct(productDto));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id, String token) {
        productService.checkOwner(id, userService.getUserByToken(getToken(token)));
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> removeByRating(Double rating, String token) {
        final Person owner = userService.getUserByToken(getToken(token)).getPerson();
        productService.removeByRating(rating, owner);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Double> sumRating(String token) {
        final Person owner = userService.getUserByToken(getToken(token)).getPerson();
        return ResponseEntity.ok(productService.getSumRating(owner));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getAllProductsBySubstring(int page, int limit, String substring, String token) {
        final Person owner = userService.getUserByToken(getToken(token)).getPerson();
        return ResponseEntity.ok(productService.getAllProductsBySubstring(substring, PageRequest.of(page, limit), owner));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getAllProductsByManufacturer(int page, int limit, Long id, String token) {
        final Person owner = userService.getUserByToken(getToken(token)).getPerson();
        final Organization manufacturer = organizationMapper.toEntity(organizationService.getOrganizationById(id));
        return ResponseEntity.ok(productService.getAllProductsByManufacturer(manufacturer, PageRequest.of(page, limit), owner));
    }

    @Override
    public ResponseEntity<Void> decreasePriceOnPercent(Integer percent, String token) {
        final Person owner = userService.getUserByToken(getToken(token)).getPerson();
        productService.decreasePriceOnPercent(percent, owner);
        return ResponseEntity.noContent().build();
    }

    private String getToken(String bearerToken) {
        return bearerToken.split(" ")[1];
    }
}
