package com.vitalisalexia.sms_backend.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping()
    public Page<Student> getStudents(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        return studentService.getStudents(pageNo, pageSize);
    }

    @PostMapping("create")
    public Student createStudent(@RequestBody Student student){
        return studentService.save(student);
    }

    @PostMapping("update")
    public Student updateStudent(@RequestBody Student student){
        return studentService.update(student);
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentService.delete(id);
    }

    @GetMapping("{id}")
    public Optional<Student> getStudentById(@PathVariable Integer id){
        return studentService.getStudentById(id);
    }

    @GetMapping("count")
    public ResponseEntity<Long> countStudents(){
        return ResponseEntity.ok(studentService.count());
    }

    @PostMapping("generate-excel/{count}")
    public void generate(@PathVariable Integer count){
        studentService.generate(count);
    }

    @PostMapping("data-process")
    public void dataProcess(){
        studentService.processData();
    }

    @PostMapping("data-upload")
    public void dataUpload(){
        studentService.uploadData();
    }
}