package com.shadaev.webify.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders_info")
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"order", "product"})
@ToString(exclude = {"order", "product"})
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
