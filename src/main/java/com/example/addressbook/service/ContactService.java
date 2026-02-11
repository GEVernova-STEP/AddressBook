package com.example.addressbook.service;

import com.example.addressbook.exception.ResourceNotFoundException;
import com.example.addressbook.model.Contact;
import com.example.addressbook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service layer contains business logic
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    //Create new contact
    public Contact createContact(Contact contact) {

        // Basic validation
        if (contact.getName() == null || contact.getName().isBlank()) {
            throw new IllegalArgumentException("Contact name must not be empty");
        }

        if (contact.getEmail() == null || contact.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        // Save contact
        return contactRepository.save(contact);
    }

    //Get all contact
    public List<Contact> getAllContacts() {

        List<Contact> contacts = contactRepository.findAll();

        // Optional business rule (not mandatory)
        if (contacts.isEmpty()) {
            throw new ResourceNotFoundException("No contacts found");
        }

        return contacts;
    }

    //Get contact by id
    public Contact getContactById(Long id) {

        return contactRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contact not found with id: " + id));
    }

    //Update contact
    public Contact updateContact(Long id, Contact updatedContact) {

        Contact existingContact = getContactById(id);

        // Update only if value is present
        if (updatedContact.getName() != null) {
            existingContact.setName(updatedContact.getName());
        }

        if (updatedContact.getEmail() != null) {
            existingContact.setEmail(updatedContact.getEmail());
        }

        if (updatedContact.getPhone() != null) {
            existingContact.setPhone(updatedContact.getPhone());
        }

        return contactRepository.save(existingContact);
    }

    //Delete contact
    public void deleteContact(Long id) {

        Contact contact = getContactById(id);

        contactRepository.delete(contact);
    }
}
