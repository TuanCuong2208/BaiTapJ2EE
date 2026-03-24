package com.example.Lab5.controller;

import com.example.Lab5.model.*;
import com.example.Lab5.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long id, HttpSession session) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
            }
            boolean exists = false;
            for (CartItem item : cart) {
                if (item.getProductId().equals(id)) {
                    item.setQuantity(item.getQuantity() + 1);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                cart.add(new CartItem(product.getId(), product.getName(), product.getPrice(), 1));
            }
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        double total = 0;
        for (CartItem item : cart) {
            total += item.getPrice() * item.getQuantity();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null && !cart.isEmpty()) {
            double total = 0;
            for (CartItem item : cart) {
                total += item.getPrice() * item.getQuantity();
            }

            Order order = new Order();
            order.setOrderDate(new Date());
            order.setTotalAmount(total);
            order = orderRepository.save(order);

            for (CartItem item : cart) {
                OrderDetail detail = new OrderDetail();
                detail.setOrder(order);
                Product p = new Product();
                p.setId(item.getProductId());
                detail.setProduct(p);
                detail.setQuantity(item.getQuantity());
                detail.setPrice(item.getPrice());
                orderDetailRepository.save(detail);
            }
            session.removeAttribute("cart");
        }
        return "redirect:/products";
    }
}