package com.shadaev.webify.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.shadaev.webify.entity.CartItem;
import com.shadaev.webify.entity.Order;
import com.shadaev.webify.entity.OrderInfo;
import com.shadaev.webify.entity.OrderStatus;
import com.shadaev.webify.repository.OrderInfoRepository;
import com.shadaev.webify.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderInfoRepository orderInfoRepository;

    public void saveOrder(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        orderRepository.save(order);
    }

    public void saveOrderInfoList(List<OrderInfo> orderInfoList) {
        orderInfoRepository.saveAll(orderInfoList);
    }

    public List<OrderInfo> cartItemListToOrderInfoList(List<CartItem> cartItemList, Order order) {
        List<OrderInfo> orderInfoList = new ArrayList<>();
        OrderInfo orderInfo;
        for (CartItem cartItem : cartItemList) {
            orderInfo = new OrderInfo(
                    cartItem.getTotalPrice(),
                    cartItem.getQuantity(),
                    cartItem.getProduct(),
                    order
            );
            orderInfoList.add(orderInfo);
        }
        return orderInfoList;
    }

    public ByteArrayOutputStream generatePdf() throws Exception {
        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
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

        for (OrderInfo orderInfo : orderInfoList) {
            LocalDateTime orderCreationDate = LocalDateTime.parse(orderInfo.getOrder().getCreatedDate().toString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));

            String orderId = orderInfo.getOrder().getId().toString();
            String formattedOrderCreationDate = orderCreationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String orderName = orderInfo.getProduct().getName();
            String orderPrice = String.valueOf(orderInfo.getProduct().getPrice());
            String orderQuantity = String.valueOf(orderInfo.getQuantity());
            String orderTotalPrice = String.valueOf(orderInfo.getTotalPrice());

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
