package com.shadaev.webify.service;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.CartProduct;
import com.shadaev.webify.entity.Order;
import com.shadaev.webify.entity.OrderProduct;
import com.shadaev.webify.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(MultiValueMap<String, String> orderData, Cart cart) {
        Order order = parseOrderData(orderData);
        order.setUser(cart.getUser());
        addProductsToOrder(order, cart.getCartProducts());
        orderRepository.save(order);
        return order;
    }

    private Order parseOrderData(MultiValueMap<String, String> orderData) {
        return new Order(
                orderData.getFirst("shippingAddress"),
                orderData.getFirst("comment"),
                Integer.parseInt(Objects.requireNonNull(orderData.getFirst("entranceNumber"))),
                Integer.parseInt(Objects.requireNonNull(orderData.getFirst("doorPassword"))),
                Integer.parseInt(Objects.requireNonNull(orderData.getFirst("floor"))),
                Integer.parseInt(Objects.requireNonNull(orderData.getFirst("apartmentNumber"))));
    }

    private void addProductsToOrder(Order order, List<CartProduct> cartProducts) {
        int quantity;
        double price;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (CartProduct cp : cartProducts) {
            quantity = cp.getQuantity();
            price = cp.getPrice() * quantity;
            orderProducts.add(new OrderProduct(order, cp.getProduct(), quantity, price));
        }
        order.setOrderProducts(orderProducts);
    }
//
//    public ByteArrayOutputStream generatePdf() throws Exception {
//        List<OrderInfo> orderInfoList = orderInfoRepository.findAll();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        Document document = new Document();
//        PdfWriter.getInstance(document, outputStream);
//
//        BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//        Font font = new Font(baseFont, 12, Font.BOLD);
//
//        Paragraph documentHeader = new Paragraph("Заказ-наряд", font);
//        documentHeader.setSpacingAfter(15);
//        documentHeader.setAlignment(Element.ALIGN_CENTER);
//
//        document.open();
//        document.add(documentHeader);
//
//        PdfPTable table = new PdfPTable(6);
//
//        PdfPCell orderIdHeaderCell = new PdfPCell(new Phrase("Номер заказа", font));
//        orderIdHeaderCell.setPadding(5);
//        orderIdHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        orderIdHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//        PdfPCell orderCreationDateHeaderCell = new PdfPCell(new Phrase("Дата заказа", font));
//        orderCreationDateHeaderCell.setPadding(5);
//        orderCreationDateHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        orderCreationDateHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//        PdfPCell orderNameHeaderCell = new PdfPCell(new Phrase("Название товара", font));
//        orderNameHeaderCell.setPadding(5);
//        orderNameHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        orderNameHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//        PdfPCell orderPriceHeaderCell = new PdfPCell(new Phrase("Цена", font));
//        orderPriceHeaderCell.setPadding(5);
//        orderPriceHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        orderPriceHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//        PdfPCell orderQuantityHeaderCell = new PdfPCell(new Phrase("Количество", font));
//        orderQuantityHeaderCell.setPadding(5);
//        orderQuantityHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        orderQuantityHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//        PdfPCell orderTotalPriceHeaderCell = new PdfPCell(new Phrase("Итого", font));
//        orderTotalPriceHeaderCell.setPadding(5);
//        orderTotalPriceHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        orderTotalPriceHeaderCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
//        table.addCell(orderIdHeaderCell);
//        table.addCell(orderCreationDateHeaderCell);
//        table.addCell(orderNameHeaderCell);
//        table.addCell(orderPriceHeaderCell);
//        table.addCell(orderQuantityHeaderCell);
//        table.addCell(orderTotalPriceHeaderCell);
//
//        for (OrderInfo orderInfo : orderInfoList) {
//            LocalDateTime orderCreationDate = LocalDateTime.parse(orderInfo.getOrder().getCreatedDate().toString(),
//                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
//
//            String orderId = orderInfo.getOrder().getId().toString();
//            String formattedOrderCreationDate = orderCreationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
//            String orderName = orderInfo.getProduct().getName();
//            String orderPrice = String.valueOf(orderInfo.getProduct().getPrice());
//            String orderQuantity = String.valueOf(orderInfo.getQuantity());
//            String orderTotalPrice = String.valueOf(orderInfo.getTotalPrice());
//
//            table.addCell(new PdfPCell(new Phrase(orderId))); // Номер заказа
//            table.addCell(new PdfPCell(new Phrase(formattedOrderCreationDate))); // Дата заказа
//            table.addCell(new PdfPCell(new Phrase(orderName))); // Название товара
//            table.addCell(new PdfPCell(new Phrase(orderPrice))); // Цена
//            table.addCell(new PdfPCell(new Phrase(orderQuantity))); // Количество
//            table.addCell(new PdfPCell(new Phrase(orderTotalPrice))); // Итого
//        }
//
//        document.add(table);
//        document.close();
//
//        return outputStream;
//    }

}
