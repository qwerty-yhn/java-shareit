package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class BookingDtoOut {


    private Long itemId;


    private final LocalDateTime start;


    private final LocalDateTime end;
}
