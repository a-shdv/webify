package com.shadaev.webify.service;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    // TODO
    //    public void createItem(CartItem item) {
    //        items.add(item);
    //    }

    // TODO
    //    public CartItem getItem(Integer id) {
    //        return items.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    //    }

    // TODO
    //    public void updateCart(Cart cart) {
    //        cartRepository.updateCart(cart);
    //    }
}
