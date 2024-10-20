package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.model.Coordinates;
import com.rubinho.lab1.model.Organization;
import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.model.UnitOfMeasure;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.ProductFilter;
import com.rubinho.lab1.rest.ProductApi;
import com.rubinho.lab1.services.ProductService;
import com.rubinho.lab1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@RestController
public class ProductApiImpl implements ProductApi {
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductApiImpl(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto, String token) {
        final User user = userService.getUserByToken(getToken(token));
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setAccessControlAllowOrigin("*");
        return new ResponseEntity<>(productService.createProduct(productDto, user), responseHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProductDto> getProductById(Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getAllProducts(int page,
                                                           int limit,
                                                           Long id,
                                                           String name,
                                                           Coordinates coordinates,
                                                           Long creationDateTimestampMs,
                                                           UnitOfMeasure unitOfMeasure,
                                                           Organization manufacturer,
                                                           Integer price,
                                                           Long manufactureCost,
                                                           Double rating,
                                                           Person owner,
                                                           User user,
                                                           String sortBy,
                                                           boolean ascending) {
        final ProductFilter productFilter = new ProductFilter(
                id,
                name,
                convertUnixTimestampToLocalDate(creationDateTimestampMs),
                coordinates,
                unitOfMeasure,
                manufacturer,
                price,
                manufactureCost,
                rating,
                owner,
                user
        );
        final Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return ResponseEntity.ok(productService.getAllProducts(PageRequest.of(page, limit, sort), productFilter));
    }

    @Override
    public ResponseEntity<ProductDto> updateProduct(ProductDto productDto, String token) {
        final User user = userService.getUserByToken(getToken(token));
        productService.checkUser(productDto.getId(), user);
        return ResponseEntity.ok(productService.updateProduct(productDto));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id, String token) {
        final User user = userService.getUserByToken(getToken(token));
        productService.checkUser(id, user);
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> removeByRating(Double rating, String token) {
        final User user = userService.getUserByToken(getToken(token));
        productService.removeByRating(rating, user);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Double> sumRating(String token) {
        final User user = userService.getUserByToken(getToken(token));
        return ResponseEntity.ok(productService.getSumRating(user));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getAllProductsBySubstring(int page,
                                                                      int limit,
                                                                      Long id,
                                                                      String name,
                                                                      Coordinates coordinates,
                                                                      Long creationDateTimestampMs,
                                                                      UnitOfMeasure unitOfMeasure,
                                                                      Organization manufacturer,
                                                                      Integer price,
                                                                      Long manufactureCost,
                                                                      Double rating,
                                                                      Person owner,
                                                                      User user,
                                                                      String sortBy,
                                                                      boolean ascending,
                                                                      String substring) {
        final ProductFilter productFilter = new ProductFilter(
                id,
                name,
                convertUnixTimestampToLocalDate(creationDateTimestampMs),
                coordinates,
                unitOfMeasure,
                manufacturer,
                price,
                manufactureCost,
                rating,
                owner,
                user
        );
        final Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return ResponseEntity.ok(productService.getAllProductsBySubstring(substring, PageRequest.of(page, limit, sort), productFilter));
    }

    @Override
    public ResponseEntity<Void> decreasePriceOnPercent(Integer percent, String token) {
        final User user = userService.getUserByToken(getToken(token));
        productService.decreasePriceOnPercent(percent, user);
        return ResponseEntity.noContent().build();
    }

    private String getToken(String bearerToken) {
        return bearerToken.split(" ")[1];
    }

    private LocalDate convertUnixTimestampToLocalDate(Long unixTimestamp) {
        if (unixTimestamp == null) {
            return null;
        }
        return Instant
                .ofEpochMilli(unixTimestamp)
                .atOffset(ZoneOffset.UTC)
                .toLocalDate();
    }
}
