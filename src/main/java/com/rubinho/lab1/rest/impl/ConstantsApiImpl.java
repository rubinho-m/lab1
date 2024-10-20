package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.model.Color;
import com.rubinho.lab1.model.Country;
import com.rubinho.lab1.model.UnitOfMeasure;
import com.rubinho.lab1.rest.ConstantsApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ConstantsApiImpl implements ConstantsApi {
    @Override
    public ResponseEntity<List<Color>> getAllColors() {
        return ResponseEntity.ok(Arrays.stream(Color.values()).toList());
    }

    @Override
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(Arrays.stream(Country.values()).toList());
    }

    @Override
    public ResponseEntity<List<UnitOfMeasure>> getAllMeasures() {
        return ResponseEntity.ok(Arrays.stream(UnitOfMeasure.values()).toList());
    }
}
