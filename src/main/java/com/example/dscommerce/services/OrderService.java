package com.example.dscommerce.services;

import com.example.dscommerce.dto.OrderDTO;
import com.example.dscommerce.entities.Order;
import com.example.dscommerce.repositories.OrderRepository;
import com.example.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found."));
        return new OrderDTO(entity);
    }
}
