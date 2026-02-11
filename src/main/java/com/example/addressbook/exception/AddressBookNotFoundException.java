package com.example.addressbook.exception;

public class AddressBookNotFoundException
        extends RuntimeException {

    public AddressBookNotFoundException(Long id) {
        super("Contact not found with id: " + id);
    }
}
