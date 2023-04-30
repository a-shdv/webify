package com.shadaev.webify.repository;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByCart(Cart cart);
}
