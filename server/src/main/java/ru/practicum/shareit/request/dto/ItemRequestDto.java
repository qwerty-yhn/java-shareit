package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;

    private String description;
    private LocalDateTime created;
    private List<ItemRequestShortDto> items;
    private Long requestorId;
}
