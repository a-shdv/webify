package com.shadaev.webify.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "user", "orderInfoList"})
@ToString(exclude = {"id", "user", "orderInfoList"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "city")
    private String city;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Nullable
    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "status")
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderInfo> orderInfoList;
}
