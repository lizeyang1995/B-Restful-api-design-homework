package com.thoughtworks.capability.gtb.restfulapidesign.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capability.gtb.restfulapidesign.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void should_add_a_student_success() throws Exception {
        Student student = Student.builder().name("lizeyang").gender("male").note("123").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStudent = objectMapper.writeValueAsString(student);
        mockMvc.perform(post("/students").content(jsonStudent).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
