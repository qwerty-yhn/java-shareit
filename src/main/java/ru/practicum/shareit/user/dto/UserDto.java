package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDto {

    private int id;
    @NotBlank(groups = {Create.class})
    private String name;

    @NotNull(groups = {Create.class})
    @Email(groups = {Create.class, Update.class})
    private String email;
}
