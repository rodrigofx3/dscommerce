package com.example.dscommerce.dto;


import com.example.dscommerce.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientDTO {

    private Long id;
    private String name;

    public ClientDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
    }

}
