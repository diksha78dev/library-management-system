package com.rit.library.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rit.library.dto.RatingRequest;
import com.rit.library.dto.RatingResponse;
import com.rit.library.model.Book;
import com.rit.library.model.Rating;
import com.rit.library.service.BookService;

/**
 * REST controller for book management and rating operations.
 * Developed by Rajsi Rajesh Shinde (Developer 2).
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * GET /api/books
     * Retrieves all books in the catalog.
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /**
     * GET /api/books/{id}
     * Retrieves a single book by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        return bookService.findBookById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "Book not found with id: " + id)));
    }

    /**
     * POST /api/books/{id}/rate
     * Accepts a rating score (1-5) and review comment for a specified book.
     *
     * @param id      ID of the book to rate
     * @param request rating request containing score (1-5) and review comment
     * @return RatingResponse with success status and rating details
     */
    @PostMapping("/{id}/rate")
    public ResponseEntity<RatingResponse> rateBook(
            @PathVariable Long id,
            @RequestBody(required = false) RatingRequest request) {

        if (request == null || request.getRating() == null
                || request.getComment() == null || request.getComment().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new RatingResponse(false, "Rating score and review comment are required"));
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            return ResponseEntity.badRequest()
                    .body(new RatingResponse(false, "Rating score must be between 1 and 5"));
        }

        if (!bookService.bookExists(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RatingResponse(false, "Book not found with id: " + id));
        }

        Rating rating = bookService.addRating(id, request);
        return ResponseEntity.ok(new RatingResponse(true, "Rating submitted successfully", rating));
    }
}
