package com.shadaev.webify.service;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.CartItem;
import com.shadaev.webify.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void saveCart(Cart cart) {
        System.out.println();
        cartRepository.save(cart);
    }

//    public void addToCart(Cart cart, CartItem cartItem) {
//        cartRepository.getById(cart.getId()).getItems().add(cartItem);
//    }

    // TODO
    //    public void createItem(CartItem item) {
    //        items.add(item);
    //    }

    // TODO
    //    public CartItem getItem(Integer id) {
    //        return items.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
    //    }
}
