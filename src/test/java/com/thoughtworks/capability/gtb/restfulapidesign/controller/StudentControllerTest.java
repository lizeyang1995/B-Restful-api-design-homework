package com.thoughtworks.capability.gtb.restfulapidesign.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import com.thoughtworks.capability.gtb.restfulapidesign.service.StudentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void should_add_a_student_success() throws Exception {
        Student student = Student.builder().name("lizeyang").gender("male").note("123").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStudent = objectMapper.writeValueAsString(student);
        mockMvc.perform(post("/students/v1").content(jsonStudent).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void should_delete_student_success() throws Exception {
        mockMvc.perform(delete("/students/1/v1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(3)
    void should_delete_student_fail_when_give_not_exists_id() throws Exception {
        mockMvc.perform(delete("/students/100/v1"))
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("要删除的id不存在")))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void should_get_all_student() throws Exception {
        mockMvc.perform(get("/students/v1"))
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void should_get_all_male_student() throws Exception {
        mockMvc.perform(get("/students/v1?gender=male"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void should_get_student_by_id() throws Exception {
        mockMvc.perform(get("/students/v1?id=2"))
                .andExpect(jsonPath("$[0].name", is("lizeyang2")))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].note", is("123")))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    void should_get_student_fail_when_id_not_exists() throws Exception {
        mockMvc.perform(get("/students/v1?id=100"))
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("要查找的id不存在")))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    void should_modify_student_by_id() throws Exception {
        Student student = Student.builder().name("lizeyang3.5").gender("male").note("1234").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStudent = objectMapper.writeValueAsString(student);
        mockMvc.perform(put("/students/3/v1").contentType(MediaType.APPLICATION_JSON).content(jsonStudent))
                .andExpect(jsonPath("$.name", is("lizeyang3.5")))
                .andExpect(jsonPath("$.gender", is("male")))
                .andExpect(jsonPath("$.note", is("1234")))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(9)
    void should_get_all_groups() throws Exception {
        mockMvc.perform(post("/students/groups/v1"))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].students", hasSize(2)))
                .andExpect(jsonPath("$[1].students", hasSize(1)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(10)
    void should_modify_group_name() throws Exception {
        mockMvc.perform(patch("/students/groups/1/v1?name=Group1.1"))
                .andExpect(jsonPath("$.name", is("Group1.1")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(status().isCreated());
    }
}
