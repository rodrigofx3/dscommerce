package com.example.dscommerce.dto;

import com.example.dscommerce.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String birthDate;
    private List<String> roles = new ArrayList<>();

    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
        birthDate = entity.getBirthDate().toString();
        entity.getRoles().forEach(role -> roles.add(role.getAuthority()));
    }

}
