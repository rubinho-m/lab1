package com.rubinho.lab1.rest;

import com.rubinho.lab1.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AdminApi {
    @GetMapping("/admin")
    ResponseEntity<List<PersonDto>> getAllPotentialAdmins(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int limit);

    @PutMapping("/admin/{id}:admin")
    ResponseEntity<Void> addAdmin(@PathVariable("id") Long id);

    @PutMapping("/admin/{id}:user")
    ResponseEntity<Void> addUser(@PathVariable("id") Long id);
}
