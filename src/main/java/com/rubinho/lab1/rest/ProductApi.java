package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.ProductDto;
import com.rubinho.lab1.model.Coordinates;
import com.rubinho.lab1.model.Organization;
import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.model.UnitOfMeasure;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

public interface ProductApi {
    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @RequestHeader(name = "Authorization") String token);

    @GetMapping("/products/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id);

    @GetMapping("/products")
    ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                    @RequestParam(defaultValue = "5") @Min(0) int limit,
                                                    @RequestParam(value = "id", required = false) Long id,
                                                    @RequestParam(value = "name", required = false) String name,
                                                    @RequestParam(value = "coordinates", required = false) Coordinates coordinates,
                                                    @RequestParam(value = "createdAt", required = false) Long creationDateTimestampMs,
                                                    @RequestParam(value = "unitOfMeasure", required = false) UnitOfMeasure unitOfMeasure,
                                                    @RequestParam(value = "manufacturer", required = false) Organization manufacturer,
                                                    @RequestParam(value = "price", required = false) Integer price,
                                                    @RequestParam(value = "manufactureCost", required = false) Long manufactureCost,
                                                    @RequestParam(value = "rating", required = false) Double rating,
                                                    @RequestParam(value = "owner", required = false) Person owner,
                                                    @RequestParam(value = "login", required = false) String login,
                                                    @RequestParam(defaultValue = "id") String sortBy,
                                                    @RequestParam(defaultValue = "true") boolean ascending);

    @PutMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @RequestHeader(name = "Authorization") String token);

    @DeleteMapping("/products/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id, @RequestHeader(name = "Authorization") String token);

    @DeleteMapping("/products/rating/{rating}")
    ResponseEntity<Void> removeByRating(@PathVariable("rating") Double rating, @RequestHeader(name = "Authorization") String token);

    @GetMapping("/products/rating")
    ResponseEntity<Double> sumRating(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/products/substring/{substring}")
    ResponseEntity<List<ProductDto>> getAllProductsBySubstring(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                               @RequestParam(defaultValue = "5") @Min(0) int limit,
                                                               @RequestParam(value = "id", required = false) Long id,
                                                               @RequestParam(value = "name", required = false) String name,
                                                               @RequestParam(value = "coordinates", required = false) Coordinates coordinates,
                                                               @RequestParam(value = "createdAt", required = false) Long creationDateTimestampMs,
                                                               @RequestParam(value = "unitOfMeasure", required = false) UnitOfMeasure unitOfMeasure,
                                                               @RequestParam(value = "manufacturer", required = false) Organization manufacturer,
                                                               @RequestParam(value = "price", required = false) Integer price,
                                                               @RequestParam(value = "manufactureCost", required = false) Long manufactureCost,
                                                               @RequestParam(value = "rating", required = false) Double rating,
                                                               @RequestParam(value = "owner", required = false) Person owner,
                                                               @RequestParam(value = "login", required = false) String login,
                                                               @RequestParam(defaultValue = "id") String sortBy,
                                                               @RequestParam(defaultValue = "true") boolean ascending,
                                                               @PathVariable("substring") String substring);

    @PutMapping("/products/price:decrease/{percent}")
    ResponseEntity<Void> decreasePriceOnPercent(@PathVariable("percent") @Min(0) @Max(100) Integer percent,
                                                @RequestHeader(name = "Authorization") String token);
}
