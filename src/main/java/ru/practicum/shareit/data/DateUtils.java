package ru.practicum.shareit.data;

import java.time.LocalDateTime;

public class DateUtils {
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDateTime nowPlusDays(Long days) {
        return LocalDateTime.now().plusDays(days);
    }
}
