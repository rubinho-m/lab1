package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.ProductDto;
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
                                                    @RequestParam(defaultValue = "5") @Min(0) int limit);

    @PutMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @RequestHeader(name = "Authorization") String token);

    @DeleteMapping("/products/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id, @RequestHeader(name = "Authorization") String token);

    @DeleteMapping("/products/{rating}")
    ResponseEntity<Void> removeByRating(@PathVariable("rating") Double rating, @RequestHeader(name = "Authorization") String token);

    @GetMapping("/products/rating")
    ResponseEntity<Double> sumRating(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/products/{substring}")
    ResponseEntity<List<ProductDto>> getAllProductsBySubstring(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                               @RequestParam(defaultValue = "5") @Min(0) int limit,
                                                               @PathVariable("substring") String substring,
                                                               @RequestHeader(name = "Authorization") String token);

    @GetMapping("/products/manufacturer/{id}")
    ResponseEntity<List<ProductDto>> getAllProductsByManufacturer(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                                  @RequestParam(defaultValue = "5") @Min(0) int limit,
                                                                  @PathVariable("id") Long id,
                                                                  @RequestHeader(name = "Authorization") String token);

    @PutMapping("/products/price:decrease/{percent}")
    ResponseEntity<Void> decreasePriceOnPercent(@PathVariable("percent") @Min(0) @Max(100) Integer percent,
                                                @RequestHeader(name = "Authorization") String token);
}
