package ru.practicum.shareit.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.practicum.shareit.constant.Constants;


@SpringBootTest
public class TestUserIdConstant {

    Constants constants = new Constants();

    @Test
    public void testUserIdConstant() {
        String expected = "X-Sharer-User-Id";
        Assertions.assertEquals(expected, constants.USER_ID);
    }

}
