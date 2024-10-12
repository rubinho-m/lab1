package com.rubinho.lab1.services;

import com.rubinho.lab1.model.Product;
import com.rubinho.lab1.repository.ProductFilter;
import org.springframework.data.jpa.domain.Specification;

public interface ProductSpecificationService {
    Specification<Product> filterBy(ProductFilter productFilter);
}
