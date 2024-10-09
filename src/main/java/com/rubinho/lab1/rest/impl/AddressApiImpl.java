package com.rubinho.lab1.rest.impl;

import com.rubinho.lab1.dto.AddressDto;
import com.rubinho.lab1.rest.AddressApi;
import com.rubinho.lab1.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@Deprecated
public class AddressApiImpl implements AddressApi {
    private final AddressService addressService;

    @Autowired
    public AddressApiImpl(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public ResponseEntity<AddressDto> createAddress(AddressDto addressDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.createAddress(addressDto));
    }

    @Override
    public ResponseEntity<AddressDto> getAddressById(Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @Override
    public ResponseEntity<List<AddressDto>> getAllAddresses(int page, int limit) {
        return ResponseEntity.ok(addressService.getAllAddresses(PageRequest.of(page, limit)));
    }

    @Override
    public ResponseEntity<AddressDto> updateAddresses(AddressDto addressDto) {
        return ResponseEntity.accepted().body(addressService.updateAddress(addressDto));
    }

    @Override
    public ResponseEntity<Void> deleteAddress(Long id) {
        addressService.deleteAddressById(id);
        return ResponseEntity.noContent().build();
    }
}
