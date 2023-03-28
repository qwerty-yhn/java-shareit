package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemRequestShortDto {
    private Long id;

    private String name;

    private String description;

    private Long ownerId;

    private Boolean available;

    private Long requestId;
}
