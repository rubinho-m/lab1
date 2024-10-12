package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.AddressDto;
import com.rubinho.lab1.rest.AddressApi;
import com.rubinho.lab1.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class AddressApiImpl implements AddressApi {
    private final AddressService addressService;

    @Autowired
    public AddressApiImpl(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }
}
