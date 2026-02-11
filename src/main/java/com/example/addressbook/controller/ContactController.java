package com.example.addressbook.controller;

import com.example.addressbook.model.Contact;
import com.example.addressbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Marks class as REST Controller
@RestController

// Base URL for all APIs in this controller
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // POST → create new contact
    @PostMapping
    public ResponseEntity<Contact> create(@RequestBody Contact contact) {

        Contact saved = contactService.createContact(contact);

        // Return 201 Created status
        return ResponseEntity.status(201).body(saved);
    }

    // GET → fetch all contacts
    @GetMapping
    public ResponseEntity<List<Contact>> getAll() {

        List<Contact> list = contactService.getAllContacts();

        return ResponseEntity.ok(list);
    }

    // GET → fetch contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getById(@PathVariable Long id) {

        Contact contact = contactService.getContactById(id);

        return ResponseEntity.ok(contact);
    }

    // PUT → update contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<Contact> update(
            @PathVariable Long id,
            @RequestBody Contact contact) {

        Contact updated = contactService.updateContact(id, contact);

        return ResponseEntity.ok(updated);
    }

    // DELETE → remove contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        contactService.deleteContact(id);

        // Return 204 No Content
        return ResponseEntity.noContent().build();
    }
}

