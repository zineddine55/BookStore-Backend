package com.example.bookstore.repository;

import com.example.bookstore.configuration.CustomProperties;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BookProxy {

    @Autowired
    private CustomProperties props;

    public Iterable<Book> getMatchingBooks(Iterable<String> keyWords) {
        String sourceApiUrl = props.getSourceApiUrl();
        String keyWordsConcatenated = StreamSupport.stream(keyWords.spliterator(), false)
                .collect(Collectors.joining("-"));

        WebClient client = WebClient.create(sourceApiUrl);
        Page page = client.get()
                .uri("/search/" + keyWordsConcatenated)
                .retrieve()
                .bodyToMono(Page.class)
                .block();

        if (page == null || page.getTotal() == 0) {
            return new ArrayList<>();
        } else {
            int nbPages = (page.getTotal() / 10) + 1;
            if(nbPages == 1) {
                return page.getBooks();
            } else {
                List<Book> matchingBooks = new ArrayList<>(page.getBooks());
                for (int i = 2; i < nbPages + 1; i++) {
                    matchingBooks.addAll(client.get()
                            .uri("/search/" + keyWordsConcatenated + "/" + i)
                            .retrieve()
                            .bodyToMono(Page.class)
                            .block()
                            .getBooks());
                }
                return matchingBooks;
            }
        }
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        String sourceApiUrl = props.getSourceApiUrl();

        WebClient client = WebClient.create(sourceApiUrl);
        Optional<Book> book = client.get()
                .uri("/books/" + isbn)
                .retrieve()
                .bodyToMono(Book.class)
                .blockOptional();

        return book;
    }
}
