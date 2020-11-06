package com.thoughtworks.capability.gtb.restfulapidesign.controller;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("students/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return student;
    }

    @DeleteMapping("students/{id}/v1")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
    }

    @GetMapping("students")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getStudents(@RequestParam(required = false) String gender) {
        return null;
    }
}
