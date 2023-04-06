package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private String authorName;
    private LocalDateTime created;

    @NotEmpty
    private String text;
}
