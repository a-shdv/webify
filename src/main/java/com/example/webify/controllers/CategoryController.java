package com.example.webify.controllers;

import com.example.webify.repositories.UserRepository;
import com.example.webify.services.CategoryService;
import com.example.webify.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/categories")
    public String categories(Model model, Principal principal) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "categories";
    }

    @GetMapping("/categories/{id}")
    public String category(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("products", categoryService.getProductListByCategoryId(id));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "category-info";
    }
}
