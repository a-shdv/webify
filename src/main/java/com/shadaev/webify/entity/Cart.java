package com.shadaev.webify.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "cart")
@EqualsAndHashCode(exclude = {"id", "cartItemList", "user"})
@ToString(exclude = {"id", "cartItemList"})
@NoArgsConstructor
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<CartItem> cartItemList;

    @Column(name = "total_price")
    private Double totalPrice = 0.0;
}
