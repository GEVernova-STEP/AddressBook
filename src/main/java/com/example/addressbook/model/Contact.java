package com.example.addressbook.model;

import jakarta.persistence.*;

// Marks this class as a JPA Entity (mapped to DB table)
@Entity
@Table(name = "contacts")
public class Contact {

    // Primary key with auto increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Column for contact name
    @Column(nullable = false)
    private String name;

    // Column for email (unique)
    @Column(unique = true)
    private String email;

    // Column for phone number
    private String phone;

    // Default constructor required by JPA
    public Contact() {}

    // Parameterized constructor
    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // -------- Getters & Setters --------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
