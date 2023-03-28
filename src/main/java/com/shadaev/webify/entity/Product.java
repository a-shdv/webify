package com.shadaev.webify.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(exclude = {"id", "category", "cartItems"})
@ToString(exclude = {"category", "cartItems"})
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Product(String name, String description, Double price,
                   String image, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "image")
    private String image;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "products_orders",
//    joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
//    inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"))
//    private List<Order> orders;
}
