package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.AddressDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface AddressApi {
    @GetMapping("/addresses")
    ResponseEntity<List<AddressDto>> getAllAddresses();
}
