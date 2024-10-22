package com.rubinho.lab1.repository;

import com.rubinho.lab1.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
    boolean existsByXAndY(Long x, Float y);
}
