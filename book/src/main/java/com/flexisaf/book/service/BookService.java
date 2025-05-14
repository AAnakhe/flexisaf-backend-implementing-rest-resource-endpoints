package com.flexisaf.book.service;

import com.flexisaf.book.exception.BookNotFoundException;
import com.flexisaf.book.exception.BookRetrievalException;
import com.flexisaf.book.exception.BookSaveException;
import com.flexisaf.book.model.Book;
import com.flexisaf.book.repository.BookRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        try {
            return bookRepository.findAll();
        } catch (DataAccessException ex) {
            throw new BookRetrievalException("Failed to retrieve books: " + ex.getMessage(), ex);
        }
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }


    public Book addBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataAccessException ex) {
            throw new BookSaveException("Failed to save the book: " + ex.getMessage(), ex);
        }
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
}
