package ru.practicum.shareit.item;

import lombok.*;
import ru.practicum.shareit.booking.BookingDtoIn;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    Long ownerId;
    //BookingDtoIn lastBooking;
    //BookingDtoIn nextBooking;
    //List<CommentDto> comments;
}
