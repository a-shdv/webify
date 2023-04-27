package com.shadaev.webify.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart_product")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"cart", "product"})
@ToString(exclude = {"cart", "product"})
public class CartProduct {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    public CartProduct(Cart cart, Product product, int quantity, double price) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
