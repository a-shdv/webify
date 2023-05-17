package com.shadaev.webify.service;

import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.shadaev.webify.entity.Order;
import com.shadaev.webify.entity.OrderProduct;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.entity.UserRole;
import com.shadaev.webify.repository.OrderProductRepository;
import com.shadaev.webify.repository.OrderRepository;
import com.shadaev.webify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private OrderProductRepository orderProductRepository;

    @Autowired
    public UserService(UserRepository userRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public User getUser(User userSession) {
        User userFromDb;
        if (userSession != null) {
            userFromDb = findUserByUsername(userSession.getUsername());
            return userFromDb;
        }
        return null;
    }

    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void createUser(User user) {
        user.setActive(true);
        user.setUserRoles(Collections.singleton(UserRole.USER));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public ByteArrayOutputStream generatePdf() throws Exception {
        PdfService pdfService = new PdfService();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        Font font = pdfService.createFont();
        PdfPCell[] pdfPCells = {
                new PdfPCell(new Phrase("Order ID", font)),
                new PdfPCell(new Phrase("Date created", font)),
                new PdfPCell(new Phrase("Product name", font)),
                new PdfPCell(new Phrase("Price per product", font)),
                new PdfPCell(new Phrase("Quantity", font)),
                new PdfPCell(new Phrase("Total price", font))
        };
        pdfService.generatePdf(outputStream, orderProducts, pdfPCells);
        return outputStream;
    }
}
