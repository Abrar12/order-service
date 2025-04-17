package com.oms.orderservice.service.impl;

import com.oms.orderservice.dto.OrderRequest;
import com.oms.orderservice.dto.OrderResponse;
import com.oms.orderservice.model.Order;
import com.oms.orderservice.repository.OrderRepository;
import com.oms.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .product(request.getProduct())
                .quantity(request.getQuantity())
                .status("CREATED")
                .build();

        Order saved = orderRepository.save(order);

        return mapToResponse(saved);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToResponse)
                .orElse(null);
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .product(order.getProduct())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .build();
    }
}
