package com.rit.library.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Request payload for rating a book.
 * Accepts rating score (1-5) and review comment.
 * Developed by Rajsi Rajesh Shinde (Developer 2).
 */
public class RatingRequest {

    @JsonAlias({"score"})
    private Integer rating;

    @JsonAlias({"reviewComment", "review"})
    private String comment;

    public RatingRequest() {
    }

    public RatingRequest(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getScore() {
        return rating;
    }

    public void setScore(Integer score) {
        this.rating = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewComment() {
        return comment;
    }

    public void setReviewComment(String reviewComment) {
        this.comment = reviewComment;
    }
}
