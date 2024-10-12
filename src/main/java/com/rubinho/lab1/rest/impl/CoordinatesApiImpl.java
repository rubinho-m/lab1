package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.CoordinatesDto;
import com.rubinho.lab1.rest.CoordinatesApi;
import com.rubinho.lab1.services.CoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CoordinatesApiImpl implements CoordinatesApi {
    private final CoordinatesService coordinatesService;

    @Autowired
    public CoordinatesApiImpl(CoordinatesService coordinatesService) {
        this.coordinatesService = coordinatesService;
    }

    @Override
    public ResponseEntity<List<CoordinatesDto>> getAllCoordinates() {
        return ResponseEntity.ok(coordinatesService.getAllCoordinates());
    }
}
