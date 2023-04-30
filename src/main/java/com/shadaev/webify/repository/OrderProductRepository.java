package com.shadaev.webify.repository;

import com.shadaev.webify.entity.CartProduct;
import com.shadaev.webify.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
