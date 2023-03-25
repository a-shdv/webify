package com.shadaev.webify.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "cart")
@EqualsAndHashCode(exclude = {"id", "items", "user"})
@ToString(exclude = {"id", "items"})
@NoArgsConstructor
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Set<CartItem> items;

    @Column(name = "sum")
    private Double sum = 0d;
}
