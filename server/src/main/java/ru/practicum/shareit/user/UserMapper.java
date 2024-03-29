package ru.practicum.shareit.user;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail()
        );
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        List<UserDto> usersDto = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            usersDto.add(UserMapper.toUserDto(users.get(i)));
        }
        return usersDto;
    }
}
