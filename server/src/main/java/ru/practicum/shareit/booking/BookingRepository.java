package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.Item;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBooker_Id(Long bookerId);

    List<Booking> findAllByBooker_IdNotAndItemIn(Long userId, List<Item> ownerItems);

    @Modifying
    @Query("UPDATE Booking b " +
            "SET b.status = :status  " +
            "WHERE b.id = :bookingId")
    void update(BookingStatus status, Long bookingId);

    List<Booking> findAllByItem_Id(Long itemId);

    List<Booking> findAllByItem_IdAndBooker_Id(Long itemId, Long bookerId);

    Page<Booking> findByBookerId(Long userId, Pageable page);

    Page<Booking> findByItem_Id(Long itemId, Pageable page);

    Page<Booking> findByItemOwner(Long ownerId, Pageable page);
}
