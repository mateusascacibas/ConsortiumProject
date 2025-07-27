package com.educationProject.consortium.controller;

import com.educationProject.consortium.config.PersonControllerTestConfig;
import com.educationProject.consortium.dto.PersonRequestDTO;
import com.educationProject.consortium.dto.PersonResponseDTO;
import com.educationProject.consortium.producer.ConsortiumGroupProducer;
import com.educationProject.consortium.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@Import(PersonControllerTestConfig.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonService service;

    @Autowired
    private ConsortiumGroupProducer producer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreatePerson() throws Exception {
        when(service.createPerson(any(PersonRequestDTO.class))).thenReturn(new PersonResponseDTO("1", "John Doe", 30, "teste", "New York"));
        PersonResponseDTO response = service.createPerson(new PersonRequestDTO("John Doe", 30, "teste", "New York"));
        mockMvc.perform(post("/person")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\",\"age\":30,\"email\":\"teste\",\"city\":\"New York\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Person created with success -> " + response));
    }

    @Test
    void shouldReturnBadRequestWhenCreatingPerson() throws Exception {
        String invalidJson = "{\"name\":\"\", \"age\":0, \"email\":\"invalid-email\", \"city\":\"\"}";
        mockMvc.perform(post("/person")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"name\":\"Name is mandatory\",\"age\":\"Age must be greater than 0\",\"email\":\"Email is mandatory and must be a valid email address\",\"city\":\"City is mandatory\"}"));
    }

    @Test
    void shouldReturnAllPersons() throws Exception {
        when(service.listAll()).thenReturn(List.of(new PersonResponseDTO("1", "John Doe", 30, "teste", "New York"),
                new PersonResponseDTO("2", "Jane Doe", 25, "teste2", "Los Angeles")));
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"name\":\"John Doe\",\"age\":30,\"email\":\"teste\",\"city\":\"New York\"}," +
                        "{\"id\":\"2\",\"name\":\"Jane Doe\",\"age\":25,\"email\":\"teste2\",\"city\":\"Los Angeles\"}]"));
    }

    @Test
    void shouldFindPersonById() throws Exception {
        when(service.findById("1")).thenReturn(new PersonResponseDTO("1", "John Doe", 30, "teste", "New York"));
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"John Doe\",\"age\":30,\"email\":\"teste\",\"city\":\"New York\"}"));
    }

    @Test
    void shouldFindPersonByName() throws Exception {
        when(service.findByName("John")).thenReturn(new PersonResponseDTO("1", "John Doe", 30, "teste", "New York"));
        mockMvc.perform(get("/person/byName/John"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"name\":\"John Doe\",\"age\":30,\"email\":\"teste\",\"city\":\"New York\"}"));
    }

}
