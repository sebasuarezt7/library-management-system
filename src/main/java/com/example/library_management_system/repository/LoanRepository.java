package com.example.library_management_system.repository;

import com.example.library_management_system.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);
    List<Loan> findByUserIdAndStatus(Long userId, String status);
    boolean existsByBookIdAndStatus(Long bookId, String status);
    List<Loan> findByBookIdAndStatus(Long bookId, String status);
    @Modifying
    void deleteByBookIdAndStatus(Long bookId, String status);
}