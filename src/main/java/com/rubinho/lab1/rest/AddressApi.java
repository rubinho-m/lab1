package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.AddressDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AddressApi {
    @Deprecated
    @PostMapping(value = "/addresses", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto);

    @Deprecated
    @GetMapping("/addresses/{id}")
    ResponseEntity<AddressDto> getAddressById(@PathVariable("id") Long id);

    @GetMapping("/addresses")
    ResponseEntity<List<AddressDto>> getAllAddresses(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int limit);

    @Deprecated
    @PutMapping(value = "/addresses", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<AddressDto> updateAddresses(@RequestBody AddressDto addressDto);

    @Deprecated
    @DeleteMapping("/addresses/{id}")
    ResponseEntity<Void> deleteAddress(@PathVariable("id") Long id);
}
