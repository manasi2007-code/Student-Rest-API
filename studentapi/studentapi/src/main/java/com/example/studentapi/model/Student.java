package com.example.studentapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String rollNumber;

    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    @Column(unique = true)
    private String email;

    private String department;

    @Column(name = "study_year")
    @Min(1) @Max(4)
    private int year;

    @DecimalMin("0.0") @DecimalMax("10.0")
    private double gpa;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
}