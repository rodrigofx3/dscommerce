package com.example.dscommerce.services;

import com.example.dscommerce.dto.OrderDTO;
import com.example.dscommerce.dto.OrderItemDTO;
import com.example.dscommerce.entities.Order;
import com.example.dscommerce.entities.OrderItem;
import com.example.dscommerce.entities.OrderStatus;
import com.example.dscommerce.entities.Product;
import com.example.dscommerce.repositories.OrderItemRepository;
import com.example.dscommerce.repositories.OrderRepository;
import com.example.dscommerce.repositories.ProductRepository;
import com.example.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final ProductRepository productRepository;

    private final OrderItemRepository OrderItemRepository;

    private final AuthService authService;

    public OrderService(OrderRepository repository, UserService userService, ProductRepository productRepository, com.example.dscommerce.repositories.OrderItemRepository orderItemRepository, AuthService authService) {
        this.orderRepository = repository;
        this.userService = userService;
        this.productRepository = productRepository;
        OrderItemRepository = orderItemRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order entity = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found."));
        authService.validateSelfOrAdmin(entity.getClient().getId());
        return new OrderDTO(entity);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        order.setClient(userService.authenticated());
        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }
        orderRepository.save(order);
        OrderItemRepository.saveAll(order.getItems());
        return new OrderDTO(order);
    }
}
