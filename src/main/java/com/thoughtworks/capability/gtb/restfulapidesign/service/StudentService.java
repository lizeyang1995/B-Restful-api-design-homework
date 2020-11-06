package com.thoughtworks.capability.gtb.restfulapidesign.service;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.exception.RequestIdNotFound;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private Map<Integer, Student> students = new HashMap<>();
    private Integer STUDENT_COUNTS = 0;

    public StudentService() {
    }

    public void addStudent(Student student) {
        student.setId(++STUDENT_COUNTS);
        students.put(STUDENT_COUNTS, student);
    }

    public void deleteStudentById(Integer id) {
        if (!students.containsKey(id)) {
            throw new RequestIdNotFound("要删除的id不存在");
        }
        students.remove(id);
    }
}
