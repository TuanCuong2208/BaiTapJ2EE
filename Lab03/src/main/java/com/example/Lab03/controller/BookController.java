package com.example.Lab03.controller;

import com.example.Lab03.model.Book;
import com.example.Lab03.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 1. Lấy danh sách sách và hiển thị giao diện
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("title", "Danh sách sách");
        return "books";
    }

    // 2. Hiển thị form thêm sách mới
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("title", "Thêm sách mới");
        return "add-book";
    }

    // 3. Xử lý thêm sách mới
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    // 4. Hiển thị form sửa sách
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            model.addAttribute("title", "Sửa sách");
            return "edit-book";
        }
        return "redirect:/books";
    }

    // 5. Xử lý cập nhật sách
    @PostMapping("/edit")
    public String updateBook(@ModelAttribute("book") Book book) {
        bookService.updateBook(book.getId(), book);
        return "redirect:/books";
    }

    // 6. Xử lý xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}