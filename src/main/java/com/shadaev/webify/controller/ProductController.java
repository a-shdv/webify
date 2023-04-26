package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Product;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.ProductService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/products")
    public String findAllProducts(@AuthenticationPrincipal User userSession, Model model) {
        List<Product> products = productService.findAllProducts();

        if (userSession != null) {
            User userFromDb = userService.findUserByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }

        model.addAttribute("products", products);
        return "/products/list";
    }

    @GetMapping("/products/{id}")
    public String findProductById(@PathVariable(value = "id") Long id,
                                  @AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findUserByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        Product product = productService.findProductById(id);

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
            User userFromDb = userService.findUserByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        List<Product> productList;

        if (filter != null && !filter.isEmpty()) {
            productList = productService.filterProductsByName(filter.trim());
        } else {
            productList = productService.findAllProducts();
        }

        model.addAttribute("products", productList);
        return "products/list";
    }
}
