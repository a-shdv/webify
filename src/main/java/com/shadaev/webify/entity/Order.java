package com.shadaev.webify.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"order\"")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Nullable
    @Column(name = "comment", columnDefinition = "text")
    private String comment;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
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
