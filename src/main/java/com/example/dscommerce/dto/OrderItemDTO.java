package com.example.dscommerce.dto;

import com.example.dscommerce.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderItemDTO {

    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;

    public OrderItemDTO(OrderItem entity) {
        productId = entity.getId().getProduct().getId();
        productName = entity.getId().getProduct().getName();
        price = entity.getPrice();
        quantity = entity.getQuantity();
    }

    public Double getSubTotal() {
        return price * quantity;
    }

}
