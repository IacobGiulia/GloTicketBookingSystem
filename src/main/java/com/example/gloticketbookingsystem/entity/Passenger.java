package com.example.gloticketbookingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passengerId;

    @Size(max = 20, message = "Firstname should be a maximum of 20 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Firstname should contain only alphabets")
    private String firstName;

    @Size(max = 20, message = "Lastname should be a maximum of 20 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Lastname should contain only alphabets")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
