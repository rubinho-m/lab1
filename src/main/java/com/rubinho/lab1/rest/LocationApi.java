package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.LocationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface LocationApi {
    @GetMapping("/locations")
    ResponseEntity<List<LocationDto>> getAllLocations();
}
