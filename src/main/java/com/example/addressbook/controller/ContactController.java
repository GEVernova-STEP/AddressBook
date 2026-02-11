package com.example.addressbook.controller;

import com.example.addressbook.dto.ContactDTO;
import com.example.addressbook.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@Slf4j
public class ContactController {

    @Autowired
    private ContactService service;

    // CREATE
    @PostMapping
    public ResponseEntity<ContactDTO> create(
            @Valid @RequestBody ContactDTO dto) {

        log.info("POST create contact");
        return ResponseEntity.status(201)
                .body(service.create(dto));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ContactDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
