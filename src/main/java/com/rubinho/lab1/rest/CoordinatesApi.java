package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.CoordinatesDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface CoordinatesApi {
    @GetMapping("/coordinates")
    ResponseEntity<List<CoordinatesDto>> getAllCoordinates();
}
