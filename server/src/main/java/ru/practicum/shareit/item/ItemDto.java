package ru.practicum.shareit.item;

import lombok.*;
import ru.practicum.shareit.booking.BookingDtoIn;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ItemDto {

    private Long id;
    private List<CommentDto> comments;
    private BookingDtoIn lastBooking;
    private BookingDtoIn nextBooking;

    private String name;

    private String description;

    private Boolean available;
    Long ownerId;
    Long requestId;
}
