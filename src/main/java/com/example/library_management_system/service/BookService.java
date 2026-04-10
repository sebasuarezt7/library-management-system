package com.example.library_management_system.service;

import com.example.library_management_system.model.Book;
import com.example.library_management_system.repository.BookRepository;
import com.example.library_management_system.repository.LoanRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public BookService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id).orElse(null);

        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setIsbn(updatedBook.getIsbn());
            existingBook.setPublishedYear(updatedBook.getPublishedYear());
            existingBook.setQuantity(updatedBook.getQuantity());
            existingBook.setAvailableQuantity(updatedBook.getAvailableQuantity());
            existingBook.setCategory(updatedBook.getCategory());

            return bookRepository.save(existingBook);
        }

        return null;
    }
    @Transactional
    public boolean deleteBook(Long id) {
        if (loanRepository.existsByBookIdAndStatus(id, "BORROWED")) {
            return false;
        }

        loanRepository.deleteByBookIdAndStatus(id, "RETURNED");
        bookRepository.deleteById(id);
        return true;
    }
}