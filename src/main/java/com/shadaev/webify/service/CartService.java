package com.shadaev.webify.service;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.CartProduct;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.repository.CartProductRepository;
import com.shadaev.webify.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    @Autowired
    public CartService(CartRepository cartRepository, CartProductRepository cartProductRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
    }

    public Cart getCartById(Long id) {
        Cart cart = cartRepository.getById(id);
        updateTotalPrice(cart);
        return cart;
    }

    public void createProduct(Cart cart, Product product, int quantity, double price) {
        CartProduct cartProduct = getCartProduct( cart.getCartProducts(), product.getId());
        if (cartProduct == null) {
            cartProduct = new CartProduct(cart, product, quantity, price);
        } else {
            cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
            cartProduct.setPrice(cartProduct.getPrice() + price);
        }
        cartProductRepository.save(cartProduct);
        updateTotalPrice(cart);
    }

    //
//    public Cart updateCartItemInCart(Product product, Integer quantity, Cart cart) {
//        List<CartItem> cartItemList = cart.getCartItemList();
//
//        CartItem cartItem = findCartItem(cartItemList, product.getId());
//
//        cartItem.setQuantity(quantity);
//        cartItem.setTotalPrice(quantity * product.getPrice());
//
//        cartItemRepository.save(cartItem);
//
//        double totalPrice = getTotalPrice(cartItemList);
//
//        cart.setTotalPrice(totalPrice);
//
//        return cartRepository.save(cart);
//    }
//
//    public Cart deleteCartItemFromCart(Product product, Cart cart) {
//        List<CartItem> cartItemList = cart.getCartItemList();
//
//        CartItem cartItem = findCartItem(cartItemList, product.getId());
//
//        cartItemList.remove(cartItem);
//
//        cartItemRepository.delete(cartItem);
//
//        double totalPrice = getTotalPrice(cartItemList);
//
//        cart.setCartItemList(cartItemList);
//        cart.setTotalPrice(totalPrice);
//
//        return cartRepository.save(cart);
//    }
//
//    public void deleteCartItemListFromCart(Cart cart) {
//        cart.getCartItemList().clear();
//        List<CartItem> cartItemList = cartItemRepository.findByCart(cart);
//        cartItemRepository.deleteAll(cartItemList);
//    }
//

    private CartProduct getCartProduct(List<CartProduct> cartProducts, Long productId) {
        for (CartProduct cp : cartProducts) {
            if (cp.getProduct().getId().equals(productId)) {
                return cp;
            }
        }
        return null;
    }

    private void updateTotalPrice(Cart cart) {
        List<CartProduct> cartProducts = cart.getCartProducts();
        double newTotalPrice = 0.0;

        for(CartProduct cartProduct : cartProducts) {
            newTotalPrice += cartProduct.getPrice();
        }
        cart.setTotalPrice(newTotalPrice);
        cartRepository.save(cart);
    }
}
