package com.example.webify.controllers;

import com.example.webify.models.Product;
import com.example.webify.services.ProductService;
import com.example.webify.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/products")
    public String products(@RequestParam(name = "name", required = false) String name, Model model,
                           Principal principal) {
        model.addAttribute("products", productService.getProducts(name));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "products";
    }

    @GetMapping("/products/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "product-info";
    }

    @PostMapping("/products/create")
    public String createProduct(Product product, Principal principal) {
        productService.saveProduct(product, principal);
        return "redirect:/";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
