package com.example.library_management_system.service;

import com.example.library_management_system.model.Book;
import com.example.library_management_system.model.LibraryUser;
import com.example.library_management_system.model.Loan;
import com.example.library_management_system.repository.BookRepository;
import com.example.library_management_system.repository.LoanRepository;
import com.example.library_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository,
                       BookRepository bookRepository,
                       UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Loan> getActiveLoansByUser(Long userId) {
        return loanRepository.findByUserIdAndStatus(userId, "BORROWED");
    }

    public List<Loan> getLoanHistoryByUser(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    public void borrowBook(Long userId, Long bookId) {
        LibraryUser user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user == null || book == null) {
            return;
        }

        if (book.getAvailableQuantity() <= 0) {
            return;
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setBorrowDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setStatus("BORROWED");

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);

        bookRepository.save(book);
        loanRepository.save(loan);
    }

    public void returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElse(null);

        if (loan == null) {
            return;
        }

        if ("RETURNED".equalsIgnoreCase(loan.getStatus())) {
            return;
        }

        loan.setReturnDate(LocalDate.now());
        loan.setStatus("RETURNED");

        Book book = loan.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);

        bookRepository.save(book);
        loanRepository.save(loan);
    }
}