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
    public String getProducts(@RequestParam(name = "name", required = false) String name,
                              @AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        List<Product> productList = productService.findProductListByName(name);

        model.addAttribute("productList", productList);
        return "products";
    }

    @GetMapping("/products/{product}")
    public String getProduct(@PathVariable(value = "product") Long productId,
                             @AuthenticationPrincipal User userSession, Model model) {
        if (userSession != null) {
            User userFromDb = userService.findByUsername(userSession.getUsername());
            model.addAttribute("user", userFromDb);
        }
        Product product = productService.findProductById(productId);

        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/user/products")
    public String getUserInfoProducts(@AuthenticationPrincipal User userSession, Model model) {
        User userFromDb = userService.findByUsername(userSession.getUsername());

        model.addAttribute("user", userFromDb);
        return "user-info-products";
    }

    @PostMapping("/products/create")
    public String createProduct(Product product) {
        productService.saveProduct(product);

        return "redirect:/";
    }

    @PostMapping("/products/delete/{product}")
    public String deleteProduct(@PathVariable(value = "product") Long productId) {
        productService.deleteProduct(productId);

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
            productList = productService.findProductListByName(filter.trim());
        } else {
            productList = productService.findProductList();
        }

        model.addAttribute("products", productList);
        return "products";
    }
}
