package com.shadaev.webify.service;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.CartItem;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.repository.CartRepository;
import com.shadaev.webify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;


    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
//    public void addToCart(Cart cart, CartItem cartItem) {
//        cartRepository.getById(cart.getId()).getItems().add(cartItem);
//    }

    // TODO
    //    public void createItem(CartItem item) {
    //        items.add(item);
    //    }

        public CartItem getItem(Long id, Principal principal) {
            User user = userRepository.findByUsername(principal.getName());
            Set<CartItem> items = user.getCart().getItems();
            return items.stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);
        }
}
