package com.rit.library.dto;

import com.rit.library.model.Rating;

/**
 * Response payload for book rating operations.
 */
public class RatingResponse {

    private boolean success;
    private String message;
    private Rating rating;

    public RatingResponse() {
    }

    public RatingResponse(boolean success, String message) {
        this(success, message, null);
    }

    public RatingResponse(boolean success, String message, Rating rating) {
        this.success = success;
        this.message = message;
        this.rating = rating;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
