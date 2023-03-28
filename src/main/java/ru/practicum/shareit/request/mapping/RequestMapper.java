package ru.practicum.shareit.request.mapping;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.*;

public class RequestMapper {

    public static ItemRequestDto toDto(ItemRequest model) {
        if (model == null) {
            return null;
        }

        ItemRequestDto.ItemRequestDtoBuilder itemRequestDto = ItemRequestDto.builder();

        itemRequestDto.requestorId(model.getRequestor().getId());
        itemRequestDto.id(model.getId());
        itemRequestDto.description(model.getDescription());
        itemRequestDto.created(model.getCreated());
        itemRequestDto.items(model.getItems());
        return itemRequestDto.build();
    }

    public static List<ItemRequestDto> toDto(List<ItemRequest> model) {

        if (model == null) {
            return null;
        }

        List<ItemRequestDto> list = new ArrayList<ItemRequestDto>(model.size());
        for (ItemRequest itemRequest : model) {
            list.add(toDto(itemRequest));
        }

        return list;
    }


}
