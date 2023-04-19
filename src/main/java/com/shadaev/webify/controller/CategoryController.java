package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Category;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.CategoryService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/categories")
    public String findAllCategories(@AuthenticationPrincipal User userSession, Model model) {
        List<Category> categories = categoryService.findAllCategories();

        if (userSession != null) {
            User userFromDb = userService.findUserByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }

        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @GetMapping("/categories/{id}")
    public String findCategoryById(@PathVariable(value = "id") Long id,
                                   @AuthenticationPrincipal User userSession, Model model) {
        List<Product> products = categoryService.findCategoryById(id);

        if (userSession != null) {
            User userFromDb = userService.findUserByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }

        model.addAttribute("products", products);
        return "categories/show";
    }
}
