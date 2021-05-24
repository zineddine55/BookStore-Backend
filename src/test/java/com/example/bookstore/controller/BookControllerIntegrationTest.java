package com.example.bookstore.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getMatchingBooksTest() throws Exception {

        mockMvc.perform(get("/books/spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(145))
                .andExpect(jsonPath("$[0].title").value("Just Spring"))
                .andExpect(jsonPath("$[0].isbn13").value("9781449306403"))
                .andExpect(jsonPath("$[1].title").value("Just Spring Integration"))
                .andExpect(jsonPath("$[1].isbn13").value("9781449316082"));

    }

    @Test
    void getBookTest() throws Exception {

        mockMvc.perform(get("/book/9781449306403"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn13").value("9781449306403"))
                .andExpect(jsonPath("$.title").value("Just Spring"));

    }
}