package com.example.delivery.controller;

import com.example.delivery.dto.OrderMenuDto;
import com.example.delivery.dto.StoreDto;
import com.example.delivery.global.security.UserDetailsImpl;
import com.example.delivery.service.order.OrderService;
import com.example.delivery.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final StoreService storeService;
    @GetMapping("/orders/stores/{storeId}")
    public String registerStoreForm(Model model, @PathVariable Integer storeId) {
        StoreDto.InfoResponse store = storeService.getStoreInfo(storeId);
        List<StoreDto.MenuInfoResponse> menus = storeService.getMenuInfoByStoreId(storeId);
        model.addAttribute("menus", menus);
        model.addAttribute("store", store);
        return "store/store-main";
    }

    @PostMapping("/orders/stores/{storeId}")
    public String createOrder(@RequestBody List<OrderMenuDto.MenuRequest> menus, @PathVariable Integer storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (orderService.createOrder(menus, userDetails.getUsername(), storeId))
            return "store/order/order-success";

        return "store/order/order-fail";
    }

    @PostMapping("/orders/{orderId}/arrived")
    public String updateArrived(Model model, @PathVariable Long orderId) {
        orderService.updateArrived(orderId);
        return "redirect:/stores/orders";
    }
}
