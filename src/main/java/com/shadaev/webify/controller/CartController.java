package com.shadaev.webify.controller;

import com.shadaev.webify.entity.Cart;
import com.shadaev.webify.entity.CartItem;
import com.shadaev.webify.entity.Product;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.service.CartService;
import com.shadaev.webify.service.ProductService;
import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/user/{user}/cart")
    public String cart(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);

        model.addAttribute("user", user);
        model.addAttribute("cart", user.getCart());
        model.addAttribute("items", user.getCart().getItems());

        return "cart";
    }

    @PostMapping("/user/{user}/cart/{cart}/add/{product}")
    public String addToCart(Cart cart, Product product) {
        Set<CartItem> items = cart.getItems();
        boolean flag = true;
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                item.setCount(item.getCount() + 1);
                cart.setSum(cart.getSum() + product.getPrice());
                flag = false;
            }
        }
        if (flag) {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setCart(cart);
            newItem.setCount(1);
            cart.setSum(cart.getSum() + product.getPrice());
            cart.getItems().add(newItem);
        }

        cartService.saveCart(cart);
        return "redirect:/categories";
    }

//
//    @RequestMapping(value = {"/cart/delete/{id}"}, method = RequestMethod.GET)
//    public ModelAndView deleteItem(@PathVariable(value = "id") Integer id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//
//        User user = userService.getUserByLogin(userName);
//        Cart cart = user.getCart();
//        Item item = cart.getItem(id);
//
//        Double itemsSum = item.getGood().getPrice() * item.getCount();
//        cart.setSum(cart.getSum() - itemsSum);
//
//        if (cart.getItems().remove(item))
//            cartService.updateCart(cart);
//
//        return cart();
//    }
//
//    @ResponseBody
//    @RequestMapping(value = {"/cart/calculate"})
//    public String cartCalculate(@RequestBody Map<String, String> json) {
//
//        Integer id = Integer.valueOf(json.get("id"));
//        Boolean isPlus = Boolean.valueOf(json.get("isPlus"));
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//
//        User user = userService.getUserByLogin(userName);
//        Cart cart = user.getCart();
//        Item item = cart.getItem(id);
//
//        if (isPlus) {
//            item.setCount(item.getCount() + 1);
//            cart.setSum(cart.getSum() + item.getGood().getPrice());
//        } else {
//            if (item.getCount() > 1) {
//                item.setCount(item.getCount() - 1);
//                cart.setSum(cart.getSum() - item.getGood().getPrice());
//            }
//        }
//        cartService.updateCart(cart);
//
//        Map<String, Object> objects = new HashMap<>();
//        objects.put("count", item.getCount());
//        objects.put("sum", cart.getSum());
//
//        return getJson(objects);
//    }
//
//    @ResponseBody
//    @RequestMapping(value = {"/cart/buy"})
//    public String buyGood(@RequestBody String id) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//
//        User user = userService.getUserByLogin(userName);
//        Good good = productService.getGoodById(Integer.valueOf(id), true);
//
//        Cart cart = user.getCart();
//        addItemInCart(good, cart);
//        cartService.updateCart(cart);
//
//        return getJson("<b>" + good.getTitle() + "</b> been successfully added in your cart!");
//    }
//
//    // Метод для преобразования Java объекта в JavaScript объект или строку
//    private String getJson(Object object) {
//        ObjectMapper mapper = new ObjectMapper();
//        String result = null;
//        try {
//            result = mapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    // Метод добавляет Item в указанную корзину
//    private void addItemInCart(Good good, Cart cart) {
//        Set<Item> items = cart.getItems();
//        boolean flag = true;
//        for (Item item : items) {
//            if (item.getGood().equals(good)) {
//                item.setCount(item.getCount() + 1);
//                cart.setSum(cart.getSum() + good.getPrice());
//                flag = false;
//            }
//        }
//        if (flag) {
//            Item newItem = new Item();
//            newItem.setGood(good);
//            newItem.setCart(cart);
//            newItem.setCount(1);
//            cart.setSum(cart.getSum() + good.getPrice());
//            cart.addItem(newItem);
//        }
//    }

}