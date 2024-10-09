package com.rubinho.lab1.services.impl;

import com.rubinho.lab1.dto.AddressDto;
import com.rubinho.lab1.mappers.AddressMapper;
import com.rubinho.lab1.repository.AddressRepository;
import com.rubinho.lab1.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDto createAddress(AddressDto addressDto) {
        return addressMapper.toDto(addressRepository.save(addressMapper.toEntity(addressDto)));
    }

    @Override
    public AddressDto getAddressById(long id) {
        return addressMapper.toDto(addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No address found by this id")));
    }

    @Override
    public List<AddressDto> getAllAddresses(Pageable paging) {
        return addressRepository.findAll(paging)
                .stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto updateAddress(AddressDto addressDto) {
        return addressMapper.toDto(addressRepository.save(addressMapper.toEntity(addressDto)));
    }

    @Override
    public void deleteAddressById(long id) {
        addressRepository.deleteById(id);
    }
}
