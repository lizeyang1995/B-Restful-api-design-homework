package com.thoughtworks.capability.gtb.restfulapidesign.controller;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Group;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
    }

    @GetMapping("students")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getStudents(@RequestParam(required = false) String gender, @RequestParam(required = false) Integer id) {
        return studentService.getStudents(gender, id);
    }

    @PutMapping("students/{id}/v1")
    @ResponseStatus(HttpStatus.OK)
    public Student modifyStudentInformation(@RequestBody Student student, @PathVariable Integer id) {
        return studentService.modifyStudent(student, id);
    }

    @PostMapping("students/groups/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Group> getGroups() {
        return studentService.getGroups();
    }

    @PatchMapping("students/groups/{id}/v1")
    @ResponseStatus(HttpStatus.CREATED)
    public Group modifyGroupInformation(@PathVariable Integer id, @RequestParam String name) {
        return studentService.modifyGroup(id, name);
    }
}
