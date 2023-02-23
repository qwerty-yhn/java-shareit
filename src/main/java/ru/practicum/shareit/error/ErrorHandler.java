package ru.practicum.shareit.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exeption.DuplicateException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ParameterNotSetException;
import ru.practicum.shareit.exeption.AlreadyExistException;


@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserNotFoundException(final ParameterNotSetException e) {
        return new ErrorResponse("",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse DuplicateException(final DuplicateException e) {
        return new ErrorResponse("",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse NotFountException(final NotFoundException e) {
        return new ErrorResponse("",
                e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse alreadyExistException(final AlreadyExistException e) {
        return new ErrorResponse("",
                e.getMessage()
        );
    }
}
