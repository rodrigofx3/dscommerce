package com.example.dscommerce.dto;

import com.example.dscommerce.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(Category entity) {
        id = entity.getId();
        name = entity.getName();
    }

}
