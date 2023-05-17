package com.shadaev.webify.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"order\"")
@Data
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "entrance_number", nullable = false)
    private int entranceNumber;

    @Column(name = "door_password", nullable = false)
    private int doorPassword;

    @Column(name = "floor", nullable = false)
    private int floor;

    @Column(name = "apartment_number", nullable = false)
    private int apartmentNumber;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Order() {
    }

    public Order(String shippingAddress, String comment,
                 int entranceNumber, int doorPassword, int floor, int apartmentNumber) {
        this.shippingAddress = shippingAddress;
        this.comment = comment;
        this.entranceNumber = entranceNumber;
        this.doorPassword = doorPassword;
        this.floor = floor;
        this.apartmentNumber = apartmentNumber;
    }

}
