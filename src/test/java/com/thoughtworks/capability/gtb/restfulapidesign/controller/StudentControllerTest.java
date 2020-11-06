package com.thoughtworks.capability.gtb.restfulapidesign.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void should_delete_student_fail_when_give_not_exists_id() throws Exception {
        mockMvc.perform(delete("/students/100/v1"))
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("要删除的id不存在")))
                .andExpect(status().isNotFound());
    }
}
