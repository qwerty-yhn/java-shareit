package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingShortMapper {
    @Mapping(target = "bookerId", source = "model.booker.id")
    BookingDtoIn toDTO(Booking model);
}
