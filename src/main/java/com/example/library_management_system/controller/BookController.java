package com.example.library_management_system.controller;

import com.example.library_management_system.model.Book;
import com.example.library_management_system.model.LibraryUser;
import com.example.library_management_system.service.BookService;
import com.example.library_management_system.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listBooks(@RequestParam(required = false) String success,
                            HttpSession session,
                            Model model) {

        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("loggedUser", user);

        if ("deleted".equals(success)) {
            model.addAttribute("success", "Book deleted successfully.");
        }

        return "main/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("title") String title,
                              HttpSession session,
                              Model model) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("books", bookService.getBooksByTitle(title));
        model.addAttribute("searchValue", title);
        model.addAttribute("loggedUser", user);
        return "main/books";
    }

    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable Long id,
                                  HttpSession session,
                                  Model model) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("loggedUser", user);
        return "main/book-details";
    }

    @GetMapping("/add")
    public String showAddBookForm(HttpSession session, Model model) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/books";
        }

        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "main/add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book, HttpSession session) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/books";
        }

        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable Long id,
                                   HttpSession session,
                                   Model model) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/books";
        }

        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "main/edit-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @ModelAttribute Book book,
                             HttpSession session) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/books";
        }

        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, HttpSession session, Model model) {
        LibraryUser user = (LibraryUser) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/books";
        }

        boolean deleted = bookService.deleteBook(id);

        if (!deleted) {
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("loggedUser", user);
            model.addAttribute("error", "This book cannot be deleted because it is currently borrowed..");
            return "main/books";
        }

        return "redirect:/books?success=deleted";
    }
}