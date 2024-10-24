package com.rubinho.lab1.repository;

import com.rubinho.lab1.model.Product;
import com.rubinho.lab1.model.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByUserAndRating(User user, Double rating);

    List<Product> findAllByUser(User user);

    @Nonnull
    Page<Product> findAll(Specification<Product> spec, @Nonnull Pageable pageable);
}
