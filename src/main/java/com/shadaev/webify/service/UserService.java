package com.shadaev.webify.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.shadaev.webify.entity.*;
import com.shadaev.webify.repository.OrderProductRepository;
import com.shadaev.webify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12, Font.BOLD);

        Paragraph documentHeader = new Paragraph("Заказ-наряд", font);
        documentHeader.setSpacingAfter(15);
        documentHeader.setAlignment(Element.ALIGN_CENTER);

        document.open();
        document.add(documentHeader);

        PdfPTable table = new PdfPTable(6);

        PdfPCell orderIdHeaderCell = new PdfPCell(new Phrase("Номер заказа", font));
        orderIdHeaderCell.setPadding(5);
        orderIdHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        orderIdHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell orderCreationDateHeaderCell = new PdfPCell(new Phrase("Дата заказа", font));
        orderCreationDateHeaderCell.setPadding(5);
        orderCreationDateHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        orderCreationDateHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell orderNameHeaderCell = new PdfPCell(new Phrase("Название товара", font));
        orderNameHeaderCell.setPadding(5);
        orderNameHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        orderNameHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell orderPriceHeaderCell = new PdfPCell(new Phrase("Цена", font));
        orderPriceHeaderCell.setPadding(5);
        orderPriceHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        orderPriceHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell orderQuantityHeaderCell = new PdfPCell(new Phrase("Количество", font));
        orderQuantityHeaderCell.setPadding(5);
        orderQuantityHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        orderQuantityHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell orderTotalPriceHeaderCell = new PdfPCell(new Phrase("Итого", font));
        orderTotalPriceHeaderCell.setPadding(5);
        orderTotalPriceHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        orderTotalPriceHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        table.addCell(orderIdHeaderCell);
        table.addCell(orderCreationDateHeaderCell);
        table.addCell(orderNameHeaderCell);
        table.addCell(orderPriceHeaderCell);
        table.addCell(orderQuantityHeaderCell);
        table.addCell(orderTotalPriceHeaderCell);

        for (OrderProduct op : orderProducts) {
            LocalDateTime orderCreationDate = LocalDateTime.parse(op.getOrder().getCreatedDate().toString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));

            String orderId = op.getOrder().getId().toString();
            String formattedOrderCreationDate = orderCreationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String orderName = op.getProduct().getName();
            String orderPrice = String.valueOf(op.getProduct().getPrice());
            String orderQuantity = String.valueOf(op.getQuantity());
            String orderTotalPrice = String.valueOf(op.getPrice());

            table.addCell(new PdfPCell(new Phrase(orderId))); // Номер заказа
            table.addCell(new PdfPCell(new Phrase(formattedOrderCreationDate))); // Дата заказа
            table.addCell(new PdfPCell(new Phrase(orderName))); // Название товара
            table.addCell(new PdfPCell(new Phrase(orderPrice))); // Цена
            table.addCell(new PdfPCell(new Phrase(orderQuantity))); // Количество
            table.addCell(new PdfPCell(new Phrase(orderTotalPrice))); // Итого
        }

        document.add(table);
        document.close();

        return outputStream;
    }
}
