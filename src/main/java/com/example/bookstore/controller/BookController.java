package com.example.bookstore.controller;

import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    BookService bookService = new BookService();

    @GetMapping("/books/{keyWords}")
    private Iterable<Book> getBooksByTitle(@PathVariable final String keyWords) {
        Iterable<String> keyWordsSplited = Arrays.asList(keyWords.split(" "));
        Iterable<Book> books = bookService.getBooks(keyWordsSplited);
        System.out.println("getBooksByTitle");
        return books;
    }

    @GetMapping("/book/{isbn}")
    private Book getBookByIsbn(@PathVariable final String isbn) {
        Optional<Book> book = bookService.getBook(isbn);
        System.out.println("getBookByIsbn...");
        return book.orElse(null);
    }
}
