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
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void should_add_a_student_success() throws Exception {
        Student student = Student.builder().name("lizeyang").gender("male").note("123").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStudent = objectMapper.writeValueAsString(student);
        mockMvc.perform(post("/students/v1").content(jsonStudent).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_delete_student_success() throws Exception {
        mockMvc.perform(delete("/students/1/v1"))
                .andExpect(status().isOk());
    }

    @Test
    void should_delete_student_fail_when_give_not_exists_id() throws Exception {
        mockMvc.perform(delete("/students/100/v1"))
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.message", is("要删除的id不存在")))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_all_student() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(status().isOk());
    }
}
