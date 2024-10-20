package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.LocationDto;
import com.rubinho.lab1.rest.LocationApi;
import com.rubinho.lab1.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationApiImpl implements LocationApi {
    private final LocationService locationService;

    @Autowired
    public LocationApiImpl(LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }
}
