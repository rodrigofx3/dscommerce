package com.example.dscommerce.dto;

import com.example.dscommerce.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMinDTO {

    private Long id;
    private String name;
    private Double price;
    private String imgUrl;

    public ProductMinDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
    }

}
