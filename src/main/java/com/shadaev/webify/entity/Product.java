package com.shadaev.webify.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@EqualsAndHashCode(exclude = {"category"})
@ToString(exclude = {"category"})
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "image")
    private String image;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
