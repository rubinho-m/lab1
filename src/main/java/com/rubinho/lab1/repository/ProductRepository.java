package com.rubinho.lab1.repository;

import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByOwnerAndRating(Person owner, Double rating);

    List<Product> findAllByOwner(Person owner, Pageable pageable);

    List<Product> findAllByOwner(Person owner);
}
