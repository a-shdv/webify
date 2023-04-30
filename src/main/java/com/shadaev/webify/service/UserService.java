package com.shadaev.webify.service;

import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.shadaev.webify.entity.OrderProduct;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.entity.UserRole;
import com.shadaev.webify.repository.OrderProductRepository;
import com.shadaev.webify.repository.UserRepository;
import com.shadaev.webify.service.helpers.PdfGenerator;
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
    private OrderProductRepository orderProductRepository;

    @Autowired
    public UserService(UserRepository userRepository, OrderProductRepository orderProductRepository) {
        this.userRepository = userRepository;
        this.orderProductRepository = orderProductRepository;
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
        user.setUserRoleSet(Collections.singleton(UserRole.USER));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public ByteArrayOutputStream generatePdf() throws Exception {
        PdfGenerator pdfGenerator = new PdfGenerator();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        Font font = pdfGenerator.createFont();
        PdfPCell[] pdfPCells = {
                new PdfPCell(new Phrase("Номер заказа", font)),
                new PdfPCell(new Phrase("Дата заказа", font)),
                new PdfPCell(new Phrase("Название товара", font)),
                new PdfPCell(new Phrase("Цена", font)),
                new PdfPCell(new Phrase("Количество", font)),
                new PdfPCell(new Phrase("Итого", font))
        };
        pdfGenerator.generatePdf(outputStream, orderProducts, pdfPCells);
        return outputStream;
    }
}
