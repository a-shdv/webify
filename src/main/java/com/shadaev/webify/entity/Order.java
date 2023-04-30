package com.shadaev.webify.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    @Column(name = "shipping_address")
    private String shippingAddress;

    @Nullable
    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @NotNull
    @Column(name = "entrance_number")
    private int entranceNumber;

    @NotNull
    @Column(name = "door_password")
    private int doorPassword;

    @NotNull
    @Column(name = "floor")
    private int floor;

    @NotNull
    @Column(name = "apartment_number")
    private int apartmentNumber;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Order() {
    }

    public Order(String shippingAddress, @Nullable String comment,
                 int entranceNumber, int doorPassword, int floor, int apartmentNumber) {
        this.shippingAddress = shippingAddress;
        this.comment = comment;
        this.entranceNumber = entranceNumber;
        this.doorPassword = doorPassword;
        this.floor = floor;
        this.apartmentNumber = apartmentNumber;
    }

}
