package com.thoughtworks.capability.gtb.restfulapidesign.service;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.exception.RequestIdNotFound;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    private Map<Integer, Student> students = new HashMap<>();
    private Integer STUDENT_COUNTS = 0;

    public StudentService() {
        students.put(1, Student.builder().id(1).name("lizeyang1").gender("male").note("123").build());
        students.put(2, Student.builder().id(2).name("lizeyang2").gender("male").note("123").build());
        students.put(3, Student.builder().id(3).name("lizeyang3").gender("female").note("123").build());
        students.put(4, Student.builder().id(4).name("lizeyang4").gender("female").note("123").build());
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

    public List<Student> getStudents(String gender, Integer id) {
        if (Optional.ofNullable(gender).isPresent()) {
            return getStudentsByGender(gender);
        }
        if (Optional.ofNullable(id).isPresent()) {
            return getStudentById(id);
        }
        Collection<Student> studentsValues = students.values();
        return new ArrayList<>(studentsValues);
    }

    private List<Student> getStudentById(Integer id) {
        if (!students.containsKey(id)) {
            throw new RequestIdNotFound("要查找的id不存在");
        }
        return Collections.singletonList(students.get(id));
    }

    private List<Student> getStudentsByGender(String gender) {
        ArrayList<Student> genderStudents = new ArrayList<>();
        for (Map.Entry<Integer, Student> entry : students.entrySet()) {
            Student student = entry.getValue();
            if (student.getGender().equals("male")) {
                genderStudents.add(student);
            }
        }
        return genderStudents;
    }
}
