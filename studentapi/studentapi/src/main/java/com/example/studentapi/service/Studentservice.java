package com.example.studentapi.service;

import com.example.studentapi.exception.StudentNotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.repository.Studentrepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Studentservice {

    private final Studentrepo repo;

    public Studentservice(Studentrepo repo) {
        this.repo = repo;
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student create(Student s) {
        return repo.save(s);
    }

    public Student update(Long id, Student s) {
        Student existing = getById(id);
        existing.setFirstName(s.getFirstName());
        existing.setLastName(s.getLastName());
        existing.setEmail(s.getEmail());
        existing.setDepartment(s.getDepartment());
        existing.setYear(s.getYear());
        existing.setGpa(s.getGpa());
        return repo.save(existing);
    }

    public void delete(Long id) {
        getById(id);   // throws 404 if the student doesn't exist
        repo.deleteById(id);
    }
}