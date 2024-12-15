package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.ProductFilter;
import com.rubinho.lab1.transactions.PrepareProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, User user);

    PrepareProductResponse prepareCreateProducts(UUID tid, List<ProductDto> productsDto, User user, boolean exception);

    boolean commit(UUID tid);

    void rollback(UUID tid);

    ProductDto getProductById(long id);

    List<ProductDto> getAllProducts(Pageable paging, ProductFilter productFilter);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProductById(long id);

    void checkUser(long id, User user);

    void removeByRating(Double rating, User user);

    Double getSumRating(User user);

    List<ProductDto> getAllProductsBySubstring(String substring, Pageable pageable, ProductFilter productFilter);

    void decreasePriceOnPercent(Integer percent, User user);
}
