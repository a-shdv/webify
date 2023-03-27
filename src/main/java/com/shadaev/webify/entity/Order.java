package com.shadaev.webify.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id","user"})
@ToString(exclude = {"id", "user"})
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

//    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private User user;

}
