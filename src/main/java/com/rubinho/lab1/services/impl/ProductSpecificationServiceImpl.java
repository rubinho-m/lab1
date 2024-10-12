package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.model.Coordinates;
import com.rubinho.lab1.model.Organization;
import com.rubinho.lab1.model.Person;
import com.rubinho.lab1.model.Product;
import com.rubinho.lab1.model.UnitOfMeasure;
import com.rubinho.lab1.model.User;
import com.rubinho.lab1.repository.ProductFilter;
import com.rubinho.lab1.services.ProductSpecificationService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CREATION_DATE = "creationDate";
    private static final String COORDINATES = "coordinates";
    private static final String UNIT_OF_MEASURE = "unitOfMeasure";
    private static final String MANUFACTURER = "manufacturer";
    private static final String PRICE = "price";
    private static final String MANUFACTURE_COST = "manufactureCost";
    private static final String RATING = "rating";
    private static final String OWNER = "owner";
    private static final String USER = "user";

    public Specification<Product> filterBy(ProductFilter productFilter) {
        return Specification
                .where(hasId(productFilter.id()))
                .and(hasName(productFilter.name()))
                .and(hasCreationDate(productFilter.creationDate()))
                .and(hasCoordinates(productFilter.coordinates()))
                .and(hasUnitOfMeasure(productFilter.unitOfMeasure()))
                .and(hasManufacturer(productFilter.manufacturer()))
                .and(hasPrice(productFilter.price()))
                .and(hasManufactureCost(productFilter.manufactureCost()))
                .and(hasRating(productFilter.rating()))
                .and(hasOwner(productFilter.owner()))
                .and(hasUser(productFilter.user()));
    }

    private Specification<Product> hasId(Long id) {
        return ((root, query, cb) -> id == null ? cb.conjunction() : cb.equal(root.get(ID), id));
    }

    private Specification<Product> hasName(String name) {
        return ((root, query, cb) -> name == null || name.isEmpty() ? cb.conjunction() : cb.equal(root.get(NAME), name));
    }

    private Specification<Product> hasCreationDate(LocalDate creationDate) {
        return ((root, query, cb) -> creationDate == null ? cb.conjunction() : cb.equal(root.get(CREATION_DATE), creationDate));
    }

    private Specification<Product> hasCoordinates(Coordinates coordinates) {
        return ((root, query, cb) -> coordinates == null ? cb.conjunction() : cb.equal(root.get(COORDINATES), coordinates));
    }

    private Specification<Product> hasUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        return ((root, query, cb) -> unitOfMeasure == null ? cb.conjunction() : cb.equal(root.get(UNIT_OF_MEASURE), unitOfMeasure));
    }

    private Specification<Product> hasManufacturer(Organization manufacturer) {
        return ((root, query, cb) -> manufacturer == null ? cb.conjunction() : cb.equal(root.get(MANUFACTURER), manufacturer));
    }

    private Specification<Product> hasPrice(Integer price) {
        return ((root, query, cb) -> price == null ? cb.conjunction() : cb.equal(root.get(PRICE), price));
    }

    private Specification<Product> hasManufactureCost(Long manufactureCost) {
        return ((root, query, cb) -> manufactureCost == null ? cb.conjunction() : cb.equal(root.get(MANUFACTURE_COST), manufactureCost));
    }

    private Specification<Product> hasRating(Double rating) {
        return ((root, query, cb) -> rating == null ? cb.conjunction() : cb.equal(root.get(RATING), rating));
    }

    private Specification<Product> hasOwner(Person owner) {
        return ((root, query, cb) -> owner == null ? cb.conjunction() : cb.equal(root.get(OWNER), owner));
    }

    private Specification<Product> hasUser(User user) {
        return ((root, query, cb) -> user == null ? cb.conjunction() : cb.equal(root.get(USER), user));
    }
}
