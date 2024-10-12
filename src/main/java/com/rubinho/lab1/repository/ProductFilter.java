package com.rubinho.lab1.repository;

import com.rubinho.lab1.model.Coordinates;
import com.rubinho.lab1.model.Organization;
import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.model.UnitOfMeasure;
import com.rubinho.lab1.model.User;

import java.time.LocalDate;

public record ProductFilter(
        Long id,
        String name,
        LocalDate creationDate,
        Coordinates coordinates,
        UnitOfMeasure unitOfMeasure,
        Organization manufacturer,
        Integer price,
        Long manufactureCost,
        Double rating,
        Person owner,
        User user
) {
}
