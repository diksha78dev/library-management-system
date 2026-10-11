package com.rit.library.model;

import java.time.LocalDateTime;

/**
 * Domain model representing a book rating and review comment.
 */
public class Rating {

    private Long id;
    private Long bookId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public Rating() {
        this.createdAt = LocalDateTime.now();
    }

    public Rating(Long id, Long bookId, int rating, String comment) {
        this.id = id;
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
