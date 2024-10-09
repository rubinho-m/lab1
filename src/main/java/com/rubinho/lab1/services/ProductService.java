package com.rubinho.lab1.services;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.model.Organization;
import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, Person owner);

    ProductDto getProductById(long id);

    List<ProductDto> getAllProducts(Pageable paging);

    ProductDto updateProduct(ProductDto productDto);

    void deleteProductById(long id);

    void checkOwner(long id, User owner);

    void removeByRating(Double rating, Person owner);

    Double getSumRating(Person owner);

    List<ProductDto> getAllProductsBySubstring(String substring, Pageable paging, Person owner);

    List<ProductDto> getAllProductsByManufacturer(Organization organization, Pageable paging, Person owner);

    void decreasePriceOnPercent(Integer percent, Person owner);
}
