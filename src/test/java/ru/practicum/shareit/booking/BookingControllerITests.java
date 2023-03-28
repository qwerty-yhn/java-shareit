package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.constant.Constants.USER_ID;

@WebMvcTest(BookingController.class)
class BookingControllerITests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingService bookingService;

    @SneakyThrows
    @Test
    void getBookingById() {
        Long userId = 1L;
        Long bookingId = 0L;

        mockMvc.perform(get("/bookings/{bookingId}", bookingId)
                        .header(USER_ID, userId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bookingService).getBookingById(bookingId, userId);
    }

    @SneakyThrows
    @Test
    void getAllBookingsOfUser() {
        Long userId = 1L;
        String state = "ALL";
        Integer offset = 0;
        Integer limit = 10;

        mockMvc.perform(get("/bookings")
                        .header(USER_ID, userId)
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bookingService).getAllBookingsByUser(userId, BookingState.ALL, offset, limit);
    }

    @SneakyThrows
    @Test
    void getAllItemsBookingsOfOwner() {

        Long userId = 1L;
        String state = "ALL";
        Integer offset = 0;
        Integer limit = 10;

        mockMvc.perform(get("/bookings/owner")
                        .header(USER_ID, userId)
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bookingService).getAllItemsBookingsByOwner(userId, BookingState.ALL, offset, limit);
    }

    @SneakyThrows
    @Test
    void addBooking_whenBookingIsCorrect_thenStatusIsOk() {
        Long userId = 1L;
        BookingDtoOut bookingDtoIn = BookingDtoOut.builder()
                .itemId(1L)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .build();

        mockMvc.perform(post("/bookings")
                        .header(USER_ID, userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingDtoIn)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(bookingService).create(userId, bookingDtoIn);
    }

    @SneakyThrows
    @Test
    void addBooking_whenBookingIsNotCorrect_thenStatusIsBadRequest() {
        Long userId = 1L;
        BookingDtoOut bookingDtoIn = BookingDtoOut.builder()
                .itemId(1L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/bookings")
                        .header(USER_ID, userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingDtoIn)))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(bookingService, never()).create(userId, bookingDtoIn);
    }

    @SneakyThrows
    @Test
    void approveBooking() {

        Long userId = 1L;
        Long bookingId = 0L;
        Boolean approved = true;

        mockMvc.perform(patch("/bookings/{bookingId}", bookingId)
                        .header(USER_ID, userId)
                        .param("approved", "true"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bookingService).approve(bookingId, userId, approved);
    }
}