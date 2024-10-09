package com.rubinho.lab1.rest;

import com.rubinho.lab1.model.Color;
import com.rubinho.lab1.model.Country;
import com.rubinho.lab1.model.UnitOfMeasure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ConstantsApi {
    @GetMapping("/colors")
    ResponseEntity<List<Color>> getAllColors();

    @GetMapping("/countries")
    ResponseEntity<List<Country>> getAllCountries();

    @GetMapping("/measures")
    ResponseEntity<List<UnitOfMeasure>> getAllMeasures();
}
