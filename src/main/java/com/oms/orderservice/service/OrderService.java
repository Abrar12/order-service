package com.oms.orderservice.service;

import com.oms.orderservice.dto.OrderRequest;
import com.oms.orderservice.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);
}
