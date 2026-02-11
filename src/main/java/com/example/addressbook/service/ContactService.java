package com.example.addressbook.service;

import com.example.addressbook.dto.ContactDTO;
import com.example.addressbook.exception.ResourceNotFoundException;
import com.example.addressbook.model.Contact;
import com.example.addressbook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Service layer manages business logic
@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    // ---------- DTO → ENTITY ----------
    private Contact toEntity(ContactDTO dto) {
        Contact c = new Contact();
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());
        return c;
    }

    // ---------- ENTITY → DTO ----------
    private ContactDTO toDTO(Contact c) {
        ContactDTO dto = new ContactDTO();
        dto.setName(c.getName());
        dto.setEmail(c.getEmail());
        dto.setPhone(c.getPhone());
        return dto;
    }

    // Create new contact
    public ContactDTO create(ContactDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Contact saved = repository.save(toEntity(dto));
        return toDTO(saved);
    }

    // Get all contact
    public List<ContactDTO> getAll() {

        List<Contact> list = repository.findAll();

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No contacts found");
        }

        return list.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //Get contact by id
    public ContactDTO getById(Long id) {

        Contact c = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contact not found: " + id));

        return toDTO(c);
    }

    //Update contact
    public ContactDTO update(Long id, ContactDTO dto) {

        Contact existing = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contact not found: " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());

        return toDTO(repository.save(existing));
    }

    //Delete contact
    public void delete(Long id) {

        Contact c = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contact not found: " + id));

        repository.delete(c);
    }
}
