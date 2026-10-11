package com.rit.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.rit.library.dto.RatingRequest;
import com.rit.library.model.Book;
import com.rit.library.model.Rating;

/**
 * Service managing books and ratings using an in-memory repository approach.
 * Note on persistence limitation:
 * Because this application currently uses in-memory data structures (ConcurrentHashMap),
 * all data (registered books and submitted ratings) is kept in JVM heap memory.
 * Data does not persist across application restarts or container recreation,
 * and is not synchronized across multiple clustered instances without a dedicated database (e.g., PostgreSQL/MySQL).
 */
@Service
public class BookService {

    private final Map<Long, Book> bookStore = new ConcurrentHashMap<>();
    private final AtomicLong ratingIdCounter = new AtomicLong(1);

    public BookService() {
        // Seed default books so existing endpoints and test suites (e.g., Postman /api/books/1/rate) can function immediately
        saveBook(new Book(1L, "Clean Code", "Robert C. Martin", "978-0132350884"));
        saveBook(new Book(2L, "The Pragmatic Programmer", "David Thomas & Andrew Hunt", "978-0201616224"));
        saveBook(new Book(3L, "Effective Java", "Joshua Bloch", "978-0134685991"));
    }

    public Book saveBook(Book book) {
        if (book != null && book.getId() != null) {
            bookStore.put(book.getId(), book);
        }
        return book;
    }

    public Optional<Book> findBookById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(bookStore.get(id));
    }

    public boolean bookExists(Long id) {
        if (id == null) {
            return false;
        }
        return bookStore.containsKey(id);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookStore.values());
    }

    public synchronized Rating addRating(Long bookId, RatingRequest request) {
        Book book = bookStore.get(bookId);
        if (book == null) {
            return null;
        }

        long ratingId = ratingIdCounter.getAndIncrement();
        Rating rating = new Rating(ratingId, bookId, request.getRating(), request.getComment());
        book.addRating(rating);
        return rating;
    }
}
