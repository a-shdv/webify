package com.shadaev.webify.service.helpers;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shadaev.webify.entity.OrderProduct;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfGenerator {

    public void generatePdf(ByteArrayOutputStream outputStream, List<OrderProduct> orderProducts, PdfPCell[] pdfPCells) throws Exception {
        Font font = createFont();
        Document document = createDocument(outputStream, font);

        PdfPTable table = new PdfPTable(pdfPCells.length);
        createTable(table, pdfPCells, orderProducts);

        document.add(table);
        document.close();
    }

    public Document createDocument(ByteArrayOutputStream outputStream, Font font) throws DocumentException {
        Document document = new Document();
        Paragraph documentHeader = createDocumentHeader(font);

        PdfWriter.getInstance(document, outputStream);

        document.open();
        document.add(documentHeader);
        return document;
    }

    private Paragraph createDocumentHeader(Font font) {
        Paragraph documentHeader = new Paragraph("Order", font);
        documentHeader.setSpacingAfter(15);
        documentHeader.setAlignment(Element.ALIGN_CENTER);
        return documentHeader;
    }

    public Font createFont() throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont();
        return new Font(baseFont, 12, Font.BOLD);
    }

    private void setFontStyles(PdfPCell pdfPCell) {
        pdfPCell.setPadding(5);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    }

    private void setFontStylesForPdfPCells(PdfPCell[] pdfPCells) {
        for (PdfPCell pdfPCell : pdfPCells) {
            setFontStyles(pdfPCell);
        }
    }

    private void createTable(PdfPTable table, PdfPCell[] pdfPCells, java.util.List<OrderProduct> orderProducts) {
        setFontStylesForPdfPCells(pdfPCells);
        for (PdfPCell pdfPCell : pdfPCells) {
            table.addCell(pdfPCell);
        }
        addOrderProductsToTable(table, orderProducts);
    }

    private void addOrderProductsToTable(PdfPTable table, List<OrderProduct> orderProducts) {
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
    }
}
