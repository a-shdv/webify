package com.shadaev.webify.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "\"order\"")
@Data
@EqualsAndHashCode(exclude = {"user", "orderInfoList"})
@ToString(exclude = {"user", "orderInfoList"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;
    @Column(name = "shipping_address")
    private String shippingAddress;

    @Nullable
    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "entrance_number")
    private int entranceNumber;

    @Column(name = "door_password")
    private int doorPassword;

    @Column(name = "floor")
    private int floor;

    @Column(name = "apartment_number")
    private int apartmentNumber;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderInfo> orderInfoList;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }
}
