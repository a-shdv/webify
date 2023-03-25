package com.shadaev.webify.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "cart")
@NoArgsConstructor
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private User user;

//    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
//    @Fetch(FetchMode.JOIN)
//    private Set<CartItem> items;

    @Column(name = "sum")
    private Double sum = 0d;
}
