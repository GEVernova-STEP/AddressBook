package com.example.addressbook.repository;

import com.example.addressbook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository handles DB operations
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    // Spring Data JPA auto-generates query from method name
    boolean existsByEmail(String email);
}
