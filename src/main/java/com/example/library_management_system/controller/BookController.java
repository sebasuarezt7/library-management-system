package com.example.library_management_system.controller;

import com.example.library_management_system.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "main/books";
    }
    @GetMapping("/search")
    public String listBooksByTitle(@RequestParam("title") String title, Model model) {
        model.addAttribute("books", bookService.getBooksByTitle(title));
        return "main/books";
    }
}
