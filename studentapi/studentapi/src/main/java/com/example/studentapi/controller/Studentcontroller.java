package com.example.studentapi.controller;

import com.example.studentapi.model.Student;
import com.example.studentapi.service.Studentservice;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class Studentcontroller {

    private final Studentservice service;

    public Studentcontroller(Studentservice service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Student create(@Valid @RequestBody Student student) {
        return service.create(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student student) {
        return service.update(id, student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
