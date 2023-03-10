package com.example.webify.controllers;

import com.example.webify.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "categories";
    }

    @GetMapping("/categories/{id}")
    public String category(@PathVariable Long id, Model model) {
        model.addAttribute("products", categoryService.getProductListByCategoryId(id));
        return "category-info";
    }
}
