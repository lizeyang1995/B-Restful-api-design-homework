package com.thoughtworks.capability.gtb.restfulapidesign.service;

import com.thoughtworks.capability.gtb.restfulapidesign.domain.Group;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.exception.RequestIdNotFound;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    private Map<Integer, Student> students = new HashMap<>();
    private Integer STUDENT_COUNTS = 7;
    private List<Group> groups = new ArrayList<>();
    private Integer GROUP_COUNTS = 6;

    public StudentService() {
        students.put(1, Student.builder().id(1).name("lizeyang1").gender("male").note("123").build());
        students.put(2, Student.builder().id(2).name("lizeyang2").gender("male").note("123").build());
        students.put(3, Student.builder().id(3).name("lizeyang3").gender("female").note("123").build());
        students.put(4, Student.builder().id(4).name("lizeyang4").gender("female").note("123").build());
        students.put(5, Student.builder().id(5).name("lizeyang5").gender("female").note("123").build());
        students.put(6, Student.builder().id(6).name("lizeyang6").gender("female").note("123").build());
        students.put(7, Student.builder().id(7).name("lizeyang7").gender("female").note("123").build());
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

    public Student modifyStudent(Student student, Integer id) {
        Student targetStudent = students.get(id);
        targetStudent.setName(student.getName());
        targetStudent.setGender(student.getGender());
        targetStudent.setNote(student.getNote());
        return targetStudent;
    }

    public List<Group> getGroups() {
        int studentSize = students.size();
        Collection<Student> studentsValues = students.values();
        ArrayList<Student> students = new ArrayList<>(studentsValues);
        List<Integer> randomNumber = getRandomNumber(studentSize, 0, studentSize);
        return randomGrouping(studentSize, students, randomNumber);

    }

    private List<Group> randomGrouping(int studentSize, List<Student> students, List<Integer> randomNumber) {
        int commonSize = studentSize / GROUP_COUNTS;
        int restCount = studentSize % GROUP_COUNTS;
        List<Group> result = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < GROUP_COUNTS; i++) {
            Group group = new Group();
            for (int j = 0; j < commonSize; j++) {
                group.getStudents().add(students.get(randomNumber.get(count)));
                count++;
            }
            result.add(group);
        }
        for (int i = 0; i < restCount; i++) {
            result.get(i).getStudents().add(students.get(randomNumber.get(count)));
            count++;
        }
        return result;
    }

    private List<Integer> getRandomNumber(int studentSize, int start, int end) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        while(numbers.size() != studentSize){
            int number = random.nextInt(end - start) + start;
            if(!numbers.contains(number)){
                numbers.add(number);
            }
        }
        return numbers;
    }
}
