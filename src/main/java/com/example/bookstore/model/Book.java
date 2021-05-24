package com.example.bookstore.model;

import lombok.Data;

@Data
public class Book {

    private String title;

    private String subtitle;

    private String authors;

    private String publisher;

    private String language;

    private String isbn10;

    private String isbn13;

    private Integer pages;

    private Integer year;

    private Integer rating;

    private String desc;

    private String price;

    private String image;

    private String url;

}
