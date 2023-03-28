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

    public Cart addItemToCart(Product product, Integer quantity, User user) {
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
        }

        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItem = findCartItem(cartItems, product.getId());
        if (cartItems == null) {
            cartItems = new ArrayList<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setPrice(quantity * product.getPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setPrice(quantity * product.getPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setPrice(cartItem.getPrice() + (quantity * product.getPrice()));
                cartItemRepository.save(cartItem);
            }
        }
        cart.setCartItems(cartItems);

        double totalPrice = totalPrice(cart.getCartItems());

        cart.setTotalPrice(totalPrice);
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    public List<CartItem> getCartItemsFromCart(Cart cart) {
        System.out.println();
        return cartRepository.getById(cart.getId()).getCartItems();
    }

    public Cart updateItemInCart(Product product, Integer quantity, User user) {
        Cart cart = user.getCart();

        List<CartItem> cartItems = cart.getCartItems();

        CartItem item = findCartItem(cartItems, product.getId());

        item.setQuantity(quantity);
        item.setPrice(quantity * product.getPrice());

        cartItemRepository.save(item);

        double totalPrice = totalPrice(cartItems);

        cart.setTotalPrice(totalPrice);

        return cartRepository.save(cart);
    }

    public Cart deleteItemFromCart(Product product, User user) {
        Cart cart = user.getCart();

        List<CartItem> cartItems = cart.getCartItems();

        CartItem item = findCartItem(cartItems, product.getId());

        cartItems.remove(item);

        cartItemRepository.delete(item);

        double totalPrice = totalPrice(cartItems);

        cart.setCartItems(cartItems);
        cart.setTotalPrice(totalPrice);

        return cartRepository.save(cart);
    }

    public void deleteCartItems(Cart cart) {
        cart.getCartItems().clear();
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(cartItems);
    }

    private CartItem findCartItem(List<CartItem> cartItems, Long productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;

        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private double totalPrice(List<CartItem> cartItems) {
        double totalPrice = 0.0;

        for (CartItem item : cartItems) {
            totalPrice += item.getPrice();
        }

        return totalPrice;
    }
}
