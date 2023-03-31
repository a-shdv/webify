package com.shadaev.webify.service;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.CartItem;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.repository.CartItemRepository;
import com.shadaev.webify.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private void saveCartItem(Product product, Integer quantity, Cart cart, List<CartItem> cartItemList) {
        CartItem cartItem;
        cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setPrice(quantity * product.getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        cartItemList.add(cartItem);
        cartItemRepository.save(cartItem);
    }

    public void saveCartItemToCart(Product product, Integer quantity, User user) {
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
        }

        List<CartItem> cartItemList = cart.getCartItemList();
        CartItem cartItem = findCartItem(cartItemList, product.getId());
        if (cartItemList == null) {
            cartItemList = new ArrayList<>();
            saveCartItem(product, quantity, cart, cartItemList);
        } else {
            if (cartItem == null) {
                saveCartItem(product, quantity, cart, cartItemList);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setPrice(cartItem.getPrice() + (quantity * product.getPrice()));
                cartItemRepository.save(cartItem);
            }
        }
        cart.setCartItemList(cartItemList);

        double totalPrice = totalPrice(cart.getCartItemList());

        cart.setTotalPrice(totalPrice);
        cart.setUser(user);

        cartRepository.save(cart);
    }

    private CartItem findCartItem(List<CartItem> cartItemList, Long productId) {
        if (cartItemList == null) {
            return null;
        }
        CartItem cartItem = null;

        for (CartItem item : cartItemList) {
            if (item.getProduct().getId() == productId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    public Cart updateCartItemInCart(Product product, Integer quantity, User user) {
        Cart cart = user.getCart();

        List<CartItem> cartItemList = cart.getCartItemList();

        CartItem cartItem = findCartItem(cartItemList, product.getId());

        cartItem.setQuantity(quantity);
        cartItem.setPrice(quantity * product.getPrice());

        cartItemRepository.save(cartItem);

        double totalPrice = totalPrice(cartItemList);

        cart.setTotalPrice(totalPrice);

        return cartRepository.save(cart);
    }

    public Cart deleteCartItemFromCart(Product product, User user) {
        Cart cart = user.getCart();

        List<CartItem> cartItemList = cart.getCartItemList();

        CartItem cartItem = findCartItem(cartItemList, product.getId());

        cartItemList.remove(cartItem);

        cartItemRepository.delete(cartItem);

        double totalPrice = totalPrice(cartItemList);

        cart.setCartItemList(cartItemList);
        cart.setTotalPrice(totalPrice);

        return cartRepository.save(cart);
    }

    public void deleteCartItems(Cart cart) {
        cart.getCartItemList().clear();
        List<CartItem> cartItemList = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(cartItemList);
    }

    private double totalPrice(List<CartItem> cartItemList) {
        double totalPrice = 0.0;

        for (CartItem cartItem : cartItemList) {
            totalPrice += cartItem.getPrice();
        }

        return totalPrice;
    }
}
