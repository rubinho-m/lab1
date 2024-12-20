package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.model.Coordinates;
import com.rubinho.lab1.model.ImportAudit;
import com.rubinho.lab1.model.Organization;
import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.model.UnitOfMeasure;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.ProductFilter;
import com.rubinho.lab1.rest.ProductApi;
import com.rubinho.lab1.services.ConverterService;
import com.rubinho.lab1.services.CoordinatorService;
import com.rubinho.lab1.services.ImportAuditService;
import com.rubinho.lab1.services.ProductService;
import com.rubinho.lab1.services.UserService;
import com.rubinho.lab1.transactions.TestingExceptions;
import com.rubinho.lab1.transactions.TransactionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@RestController
public class ProductApiImpl implements ProductApi {
    private final ProductService productService;
    private final ConverterService converterService;
    private final UserService userService;
    private final ImportAuditService importAuditService;
    private final CoordinatorService coordinatorService;

    @Autowired
    public ProductApiImpl(ProductService productService,
                          UserService userService,
                          ConverterService converterService,
                          ImportAuditService importAuditService,
                          CoordinatorService coordinatorService) {
        this.productService = productService;
        this.userService = userService;
        this.converterService = converterService;
        this.importAuditService = importAuditService;
        this.coordinatorService = coordinatorService;
    }

    @Override
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto, String token) {
        final User user = userService.getUserByToken(getToken(token));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(productDto, user));
    }

    @Override
    public ResponseEntity<List<ProductDto>> createProductsFromFile(MultipartFile file,
                                                                   boolean dbException,
                                                                   boolean s3Exception,
                                                                   boolean runtimeException,
                                                                   String token) {
        try {
            final User user = userService.getUserByToken(getToken(token));
            final String content = new String(file.getBytes());
            final List<ProductDto> productsDto = converterService.toList(content);
            try {
                final List<ProductDto> created = coordinatorService.twoPhaseCommit(new TransactionData(
                        productsDto,
                        file,
                        user,
                        new TestingExceptions(dbException, s3Exception, runtimeException)
                ));
                importAuditService.addImportAudit(
                        new ImportAudit(null, true, created.size(), user, file.getOriginalFilename())
                );
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(created);
            } catch (Exception e) {
                importAuditService.addImportAudit(
                        new ImportAudit(null, false, 0, user, file.getOriginalFilename())
                );
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка импорта");
            }

        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
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
                                                           String login,
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
                userService.getUserByLogin(login)
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
                                                                      String login,
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
                userService.getUserByLogin(login)
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
