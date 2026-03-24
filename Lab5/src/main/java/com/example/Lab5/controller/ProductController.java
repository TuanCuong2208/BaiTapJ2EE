package com.example.Lab5.controller;

import com.example.Lab5.model.Product;
import com.example.Lab5.service.CategoryService;
import com.example.Lab5.service.ProductService;
import com.example.Lab5.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String listProducts(Model model,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "categoryId", required = false) Long categoryId,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "sort", defaultValue = "asc") String sort) {

        int pageSize = 5;
        Sort sortOrder = sort.equalsIgnoreCase("desc") ? Sort.by("price").descending() : Sort.by("price").ascending();
        Pageable pageable = PageRequest.of(page - 1, pageSize, sortOrder);
        Page<Product> productPage;

        if (keyword != null && !keyword.isEmpty() && categoryId != null) {
            productPage = productRepository.findByNameContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
        } else if (keyword != null && !keyword.isEmpty()) {
            productPage = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else if (categoryId != null) {
            productPage = productRepository.findByCategoryId(categoryId, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sort", sort);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "product/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/add";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }
}