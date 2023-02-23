package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@AllArgsConstructor
@Data
public class User {
    private int id;

    private String name;

    @Email
    private String email;

}
