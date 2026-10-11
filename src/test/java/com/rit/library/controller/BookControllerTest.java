package com.rit.library.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.rit.library.service.BookService;

/**
 * Unit tests for BookController using MockMvc.
 * Developed by Rajsi Rajesh Shinde (Developer 2).
 */
@WebMvcTest(BookController.class)
@Import(BookService.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("POST /api/books/{id}/rate succeeds with valid score and comment")
    void rateBookWithValidDataReturnsOk() throws Exception {
        String requestJson = """
                {
                    "rating": 5,
                    "comment": "Outstanding book on software craftsmanship and clean code practices!"
                }
                """;

        mockMvc.perform(post("/api/books/1/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Rating submitted successfully"))
                .andExpect(jsonPath("$.rating.rating").value(5))
                .andExpect(jsonPath("$.rating.bookId").value(1))
                .andExpect(jsonPath("$.rating.comment").value("Outstanding book on software craftsmanship and clean code practices!"))
                .andExpect(jsonPath("$.rating.id").isNumber());
    }

    @Test
    @DisplayName("POST /api/books/{id}/rate succeeds using score and reviewComment aliases")
    void rateBookWithAliasesReturnsOk() throws Exception {
        String requestJson = """
                {
                    "score": 4,
                    "reviewComment": "Very practical and well written."
                }
                """;

        mockMvc.perform(post("/api/books/2/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.rating.rating").value(4));
    }

    @Test
    @DisplayName("POST /api/books/{id}/rate fails when rating is greater than 5")
    void rateBookWithRatingGreaterThanFiveReturnsBadRequest() throws Exception {
        String requestJson = """
                {
                    "rating": 6,
                    "comment": "Rating is too high"
                }
                """;

        mockMvc.perform(post("/api/books/1/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Rating score must be between 1 and 5"));
    }

    @Test
    @DisplayName("POST /api/books/{id}/rate fails when rating is less than 1")
    void rateBookWithRatingLessThanOneReturnsBadRequest() throws Exception {
        String requestJson = """
                {
                    "rating": 0,
                    "comment": "Rating is too low"
                }
                """;

        mockMvc.perform(post("/api/books/1/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Rating score must be between 1 and 5"));
    }

    @Test
    @DisplayName("POST /api/books/{id}/rate fails when comment is empty or missing")
    void rateBookWithMissingCommentReturnsBadRequest() throws Exception {
        String requestJson = """
                {
                    "rating": 4,
                    "comment": ""
                }
                """;

        mockMvc.perform(post("/api/books/1/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Rating score and review comment are required"));
    }

    @Test
    @DisplayName("POST /api/books/{id}/rate fails when rating score is missing")
    void rateBookWithMissingScoreReturnsBadRequest() throws Exception {
        String requestJson = """
                {
                    "comment": "Good book but no score provided"
                }
                """;

        mockMvc.perform(post("/api/books/1/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Rating score and review comment are required"));
    }

    @Test
    @DisplayName("POST /api/books/{id}/rate fails with nonexistent book ID")
    void rateBookWithNonexistentBookIdReturnsNotFound() throws Exception {
        String requestJson = """
                {
                    "rating": 5,
                    "comment": "Trying to rate a nonexistent book"
                }
                """;

        mockMvc.perform(post("/api/books/9999/rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Book not found with id: 9999"));
    }

    @Test
    @DisplayName("GET /api/books returns seeded book catalog")
    void getAllBooksReturnsList() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
}
