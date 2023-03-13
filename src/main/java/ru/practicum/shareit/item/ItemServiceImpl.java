package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.error.BadRequestException;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final BookingShortMapper bookingShortMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional
    @Override
    public Item createItem(Long idOwner, Item item) {
        item.setOwner(userService.getUser(idOwner));
        return itemRepository.save(item);
    }

    @Transactional
    @Override
    public Item updateItem(Long idOwner, Long idItem, Item item) {

        Item itemPrev = getItem(idItem);
        if (itemPrev.getOwner().getId() != idOwner) {
            throw new NotFoundException(" not valid Owner(" + idOwner + ") of object");
        }
        if (item.getName() == null) {
            item.setName(itemPrev.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(itemPrev.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(itemPrev.getAvailable());
        }
        item.setOwner(userService.getUser(idOwner));
        item.setId(idItem);
        return itemRepository.save(item);
    }

    @Transactional
    @Override
    public ItemDto getItemById(Long idOwner, Long idItem) {
        Item item = getItem(idItem);
        if (item.getOwner().getId().equals(idOwner)) {
            return itemSetComment(itemSetBooking(itemMapper.toItemDto(item)));
        }
        return itemSetComment(itemMapper.toItemDto(item));
    }

    public Item getItem(Long idItem) {
        return itemRepository.findById(idItem).orElseThrow(() -> new NotFoundException("Not found item = " + idItem));
    }

    @Transactional
    @Override
    public List<Item> getAllItems(Long idOwner) {
        return null;
    }

    @Transactional
    @Override
    public List<Item> getItemsByText(String text) {
        return itemRepository.search(text)
                .stream()
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentDto createComment(Long itemId, Long userId, CommentDto commentDto) {

        List<Booking> bookings = bookingRepository.findAllByItem_IdAndBooker_Id(itemId, userId);
        LocalDateTime now = LocalDateTime.now();
        Comment comment = new Comment();

        if (bookings.stream().anyMatch(x -> !x.getStatus().equals(BookingStatus.REJECTED) && x.getStart().isBefore(now))) {
            comment = Comment.builder()
                    .text(commentDto.getText())
                    .author(bookings.get(0).getBooker())
                    .item(bookings.get(0).getItem())
                    .created(LocalDateTime.now())
                    .build();
            commentRepository.save(comment);
        } else {
            throw new BadRequestException("");
        }
        return commentMapper.toDTO(comment);
    }

    @Transactional
    @Override
    public ItemDto itemSetBooking(ItemDto itemDto) {
        itemDto.setLastBooking(getLastNextItemBookings(itemDto).get(0));
        itemDto.setNextBooking(getLastNextItemBookings(itemDto).get(1));
        return itemDto;
    }

    @Transactional
    @Override
    public ItemDto itemSetComment(ItemDto itemDto) {
        itemDto.setComments(getItemComment(itemDto));
        return itemDto;
    }

    private List<BookingDtoIn> getLastNextItemBookings(ItemDto itemDto) {
        List<BookingDtoIn> lastNextBokingsTemp = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        List<Booking> itemBookings = bookingRepository.findAllByItem_Id(itemDto.getId());
        lastNextBokingsTemp.add(0, bookingShortMapper.toDTO(itemBookings.stream()
                .filter(x -> !(x.getStatus().equals(BookingStatus.REJECTED)))
                .filter(x -> x.getEnd().isBefore(now))
                .sorted(Comparator.comparing(Booking::getStart)).findFirst().orElse(null)));
        lastNextBokingsTemp.add(1, bookingShortMapper.toDTO(itemBookings.stream()
                .filter(x -> !(x.getStatus().equals(BookingStatus.REJECTED)))
                .filter(x -> x.getStart().isAfter(now))
                .sorted(Comparator.comparing(Booking::getStart)).findFirst().orElse(null)));
        return lastNextBokingsTemp;
    }

    private List<CommentDto> getItemComment(ItemDto itemDto) {
        return commentRepository.findAllByItem_Id(itemDto.getId()).stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

}
