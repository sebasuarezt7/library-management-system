package com.example.library_management_system.controller;

import com.example.library_management_system.model.LibraryUser;
import com.example.library_management_system.service.LoanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public String showBorrowedBooks(HttpSession session, Model model) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("loans", loanService.getActiveLoansByUser(user.getId()));
        model.addAttribute("loggedUser", user);
        return "main/loans";
    }

    @GetMapping("/history")
    public String showLoanHistory(HttpSession session, Model model) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("loans", loanService.getLoanHistoryByUser(user.getId()));
        model.addAttribute("loggedUser", user);
        return "main/loan-history";
    }

    @GetMapping("/borrow/{bookId}")
    public String borrowBook(@PathVariable Long bookId, HttpSession session) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        loanService.borrowBook(user.getId(), bookId);
        return "redirect:/loans";
    }

    @GetMapping("/return/{loanId}")
    public String returnBook(@PathVariable Long loanId, HttpSession session) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        loanService.returnBook(loanId);
        return "redirect:/loans";
    }
}