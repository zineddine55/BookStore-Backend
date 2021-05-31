package com.example.bookstore.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static java.util.Optional.of;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void setUp() {

        Book book1 = new Book();
        String isbn1 = "9781449306403";
        String title1 = "Just Spring";
        book1.setIsbn13(isbn1);
        book1.setTitle(title1);

        Book book2 = new Book();
        String isbn2 = "9781449316082";
        String title2 = "Just Spring Integration";
        book2.setIsbn13(isbn2);
        book2.setTitle(title2);

        List books = Arrays.asList(book1, book2);
        String keyWords = "spring";

        when(bookService.getBook(isbn1)).thenReturn(of(book1));

        when(bookService.getBooks(Arrays.asList(keyWords.split(" ")))).thenReturn(books);
    }

    @Test
    void getMatchingBooksTest() throws Exception {

        mockMvc.perform(get("/books/spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
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