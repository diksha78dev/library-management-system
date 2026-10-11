package com.rit.library.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Domain model representing a library book.
 */
public class Book {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private final List<Rating> ratings = new ArrayList<>();

    public Book() {
    }

    public Book(Long id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public synchronized void addRating(Rating rating) {
        if (rating != null) {
            ratings.add(rating);
        }
    }

    public synchronized List<Rating> getRatings() {
        return Collections.unmodifiableList(new ArrayList<>(ratings));
    }

    public synchronized double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Rating r : ratings) {
            sum += r.getRating();
        }
        return sum / ratings.size();
    }
}
