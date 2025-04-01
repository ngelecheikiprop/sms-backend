package com.vitalisalexia.sms_backend.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;

    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    public Student save(Student student) {
        return studentRepo.save(student);
    }

    public Student update(Student student){
        Optional<Student> studentOptional = studentRepo.findById(student.getId());
        if(studentOptional.isPresent())
            return studentRepo.save(student);
        return null;
    }

    public Optional<Student> delete(int id) {
        Optional<Student> studentOptional = studentRepo.findById(id);
        studentOptional.ifPresent(student -> {
            student.setStatus(0);
            studentRepo.save(student);
        });
        return studentOptional;

    }


    public Optional<Student> getStudentById(Integer id) {
        return studentRepo.findById(id);
    }

    public Long count() {
        return studentRepo.count();
    }
}