package com.shadaev.webify.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class CartProductId implements Serializable {
    private Long cartId;
    private Long productId;
}
