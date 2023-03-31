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

    @GetMapping("/categories/{category}")
    public String getCategory(@PathVariable(value = "category") Long categoryId,
                              @AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        List<Product> productList = categoryService.findProductListByCategoryId(categoryId);

        model.addAttribute("productList", productList);
        return "category";
    }

    @GetMapping("/categories")
    public String getCategories(@AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        List<Category> categoryList = categoryService.findCategoryList();

        model.addAttribute("categoryList", categoryList);
        return "categories";
    }
}
