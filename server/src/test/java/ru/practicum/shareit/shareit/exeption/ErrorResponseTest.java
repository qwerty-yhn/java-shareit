package ru.practicum.shareit.shareit.exeption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.error.ErrorResponse;
import ru.practicum.shareit.error.InternalServerErrorException;

@SpringBootTest
public class ErrorResponseTest {

    ErrorResponse errorResponse = new ErrorResponse("");

    @Test
    void errorResponseTest() {
        Assertions.assertNotNull(errorResponse);
    }

    @Test
    public void testConstructorAndGetMessage() {
        String message = "Internal Server Error";
        InternalServerErrorException exception = new InternalServerErrorException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }
}
