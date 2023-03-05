package com.example.webify.controllers;

import com.example.webify.models.Product;
import com.example.webify.services.CategoryService;
import com.example.webify.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "categories-info";
    }

//
//    @GetMapping("/product/{id}")
//    public String productInfo(@PathVariable Long id, Model model) {
//        model.addAttribute("product", productService.getProductById(id));
//        return "product-info";
//    }
//
//    @PostMapping("product/create")
//    public String createProduct(Product product) {
//        productService.saveProduct(product);
//        return "redirect:/";
//    }
//
//    @PostMapping("/product/delete/{id}")
//    public String deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return "redirect:/";
//    }
}
