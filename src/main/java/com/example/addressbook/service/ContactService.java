package com.example.addressbook.service;

import com.example.addressbook.dto.ContactDTO;
import com.example.addressbook.exception.AddressBookNotFoundException;
import com.example.addressbook.model.Contact;
import com.example.addressbook.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactService {

    @Autowired
    private ContactRepository repository;

    private Contact toEntity(ContactDTO dto) {
        Contact c = new Contact();
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());
        return c;
    }

    private ContactDTO toDTO(Contact c) {
        return new ContactDTO(
                c.getName(),
                c.getEmail(),
                c.getPhone()
        );
    }

    public ContactDTO create(ContactDTO dto) {

        log.info("Creating contact {}", dto.getEmail());

        Contact saved = repository.save(
                toEntity(dto));

        return toDTO(saved);
    }

    public List<ContactDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO getById(Long id) {

        Contact c = repository.findById(id)
                .orElseThrow(() ->
                        new AddressBookNotFoundException(id));

        return toDTO(c);
    }

    public ContactDTO update(Long id, ContactDTO dto) {

        Contact c = repository.findById(id)
                .orElseThrow(() ->
                        new AddressBookNotFoundException(id));

        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());

        return toDTO(repository.save(c));
    }

    public void delete(Long id) {

        Contact c = repository.findById(id)
                .orElseThrow(() ->
                        new AddressBookNotFoundException(id));

        repository.delete(c);
    }
}
