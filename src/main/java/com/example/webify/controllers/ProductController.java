package com.example.webify.controllers;

import com.example.webify.models.Product;
import com.example.webify.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    // final - чтобы Spring при создании бина сразу этот бин инжектил
    private final ProductService productService;


    // Model - чтобы передавать данные в шаблонизатор
    @GetMapping("/")
    public String products(Model model) {
        // Теперь на html-странице мы сможем обрабатывать данные из списка продуктов
        model.addAttribute("products", productService.getProducts()); // ключ => значение
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product-info";
    }

    @PostMapping("product/create")
    public String createProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
