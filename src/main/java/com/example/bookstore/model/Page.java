package com.example.bookstore.model;

import lombok.Data;

import java.util.List;

@Data
public class Page {

    private Integer total;

    private Integer page;

    private List<Book> books;

}
