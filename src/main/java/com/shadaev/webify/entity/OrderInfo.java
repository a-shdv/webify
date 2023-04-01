package com.shadaev.webify.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders_info")
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "quantity")
    private int quantity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderInfo(double totalPrice, int quantity,
                     Product product, Order order) {
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }
}
