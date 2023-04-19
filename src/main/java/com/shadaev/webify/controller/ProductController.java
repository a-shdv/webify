package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Product;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.ProductService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/products")
    public String getAllProducts(@AuthenticationPrincipal User userSession, Model model) {
        List<Product> products = productService.getAllProducts();

        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }

        model.addAttribute("products", products);
        return "/products/list";
    }

    @GetMapping("/products/{id}")
    public String getProductById(@PathVariable(value = "id") Long id,
                                 @AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        Product product = productService.getProductById(id);

        model.addAttribute("product", product);
        return "products/show";
    }

    @PostMapping("/products")
    public String createProduct(Product product) {
        productService.createProduct(product);

        return "redirect:/";
    }

    @PostMapping("/products/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        productService.deleteProduct(id);

        return "redirect:/";
    }

    @PostMapping("/products/filter")
    public String filterProduct(@RequestParam String filter,
                                @AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        List<Product> productList;

        if (filter != null && !filter.isEmpty()) {
            productList = productService.filterProductsByName(filter.trim());
        } else {
            productList = productService.getAllProducts();
        }

        model.addAttribute("products", productList);
        return "products/list";
    }
}
