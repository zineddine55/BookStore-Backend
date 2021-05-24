package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookProxy bookProxy;

    public Iterable<Book> getBooks(Iterable<String> keyWords) {
        return bookProxy.getMatchingBooks(keyWords);
    }

    public Optional<Book> getBook(String isbn) {
        return bookProxy.getBookByIsbn(isbn);
    }
}
