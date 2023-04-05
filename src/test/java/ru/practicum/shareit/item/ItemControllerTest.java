package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto;
import ru.practicum.shareit.user.UserService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;
    //ItemRequest itemRequest = new ItemRequest(1L, "описание", null, null, null);
    private final ItemDto itemDto = new ItemDto(
            1L,
            new ArrayList<>(),
            null,
            null,
            "Какая-то вещь",
            "Какое-то описание",
            true,
            null,
            1L);
    private final User user = new User(
            1L,
            "Igor",
            "igor@gmail.dom");
    private final Item item = new Item(1L, "Какая-то вещь", "Какое-то описание", true, null, null);

    private final UserDto userDto = new UserDto(
            1L,
            "Igor",
            "igor@gmail.dom");


    private final CommentDto commentDto = new CommentDto(
            1L,
            "Igor",
            LocalDateTime.now()
                    .withNano(0),
            "Коммент"
    );

    @BeforeEach
    void setup() {
        when(userService.getUser(anyLong())).thenReturn(user);
    }

    @Test
    void createNewItem() throws Exception {
        when(itemService.createItem(anyLong(), any(Item.class), anyLong())).thenReturn(item);

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.ownerId", is(itemDto.getOwnerId()), Long.class))
                .andExpect(jsonPath("$.lastBooking", is(itemDto.getLastBooking())))
                .andExpect(jsonPath("$.nextBooking", is(itemDto.getNextBooking())));

    }

    @Test
    void getAllItemsByUserId() throws Exception {
        when(itemService.getItemById(anyLong(), anyLong())).thenReturn(itemDto);

        mvc.perform(get("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk());

    }

    @Test
    void addComment() throws Exception {
        when(itemService.getItemById(anyLong(), anyLong())).thenReturn(itemDto);
        when(itemService.createComment(anyLong(), anyLong(), any(CommentDto.class))).thenReturn(commentDto);

        mvc.perform(post("/items/{id}/comment", "1")
                        .content(mapper.writeValueAsString(commentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentDto.getId()), Long.class))
                .andExpect(jsonPath("$.text", is(commentDto.getText())))
                .andExpect(jsonPath("$.authorName", is(commentDto.getAuthorName())))
                .andExpect(jsonPath("$.created", is(commentDto.getCreated().toString())));
    }

    @Test
    void getItemById() throws Exception {
        when(itemService.getItemById(any(), any())).thenReturn(itemDto);

        mvc.perform(get("/items/{id}", "1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.ownerId", is(itemDto.getOwnerId()), Long.class))
                .andExpect(jsonPath("$.lastBooking", is(itemDto.getLastBooking())))
                .andExpect(jsonPath("$.nextBooking", is(itemDto.getNextBooking())))
                .andExpect(jsonPath("$.comments", hasSize(0)))
                .andExpect(jsonPath("$.requestId", is(itemDto.getRequestId()), Long.class));
    }

    @Test
    void getItemByText() throws Exception {
        when(itemService.getItemsByText(any())).thenReturn(List.of(item));

        mvc.perform(get("/items/search")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1")
                        .param("text", "text")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", is(itemDto.getName())))
                .andExpect(jsonPath("$[0].description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$[0].ownerId", is(itemDto.getOwnerId()), Long.class))
                .andExpect(jsonPath("$[0].lastBooking", is(itemDto.getLastBooking())))
                .andExpect(jsonPath("$[0].nextBooking", is(itemDto.getNextBooking())));
    }

    @Test
    void updateItemById() throws Exception {
        when(itemService.updateItem(anyLong(), anyLong(), any(Item.class))).thenReturn(item);

        mvc.perform(patch("/items/{id}", "1")
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.ownerId", is(itemDto.getOwnerId()), Long.class))
                .andExpect(jsonPath("$.lastBooking", is(itemDto.getLastBooking())))
                .andExpect(jsonPath("$.nextBooking", is(itemDto.getNextBooking())));
    }


}
