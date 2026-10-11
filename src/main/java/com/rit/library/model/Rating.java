package com.rit.library.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Domain model representing a book rating and review comment.
 */
public class Rating {

    private Long id;
    private Long bookId;
    private int score;
    private String comment;
    private LocalDateTime createdAt;

    public Rating() {
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
    }

    public Rating(Long id, Long bookId, int score, String comment) {
        this.id = id;
        this.bookId = bookId;
        this.score = score;
        this.comment = comment;
        this.createdAt = LocalDateTime.now(ZoneId.systemDefault());
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRating() {
        return score;
    }

    public void setRating(int rating) {
        this.score = rating;
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
