package ru.practicum.shareit.request.mapping;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.request.dto.ItemRequestShortDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestShortMapper {

    public static ItemRequestShortDto toDto(Item model) {

        return ItemRequestShortDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .ownerId(model.getOwner().getId())
                .available(model.getAvailable())
                .requestId(model.getItemRequest().getId())
                .build();
    }

    public static List<ItemRequestShortDto> toDto(List<Item> model) {

        List<ItemRequestShortDto> list = new ArrayList<ItemRequestShortDto>(model.size());
        for (Item item : model) {
            list.add(toDto(item));
        }
        return list;
    }
}
