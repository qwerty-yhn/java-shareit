package ru.practicum.shareit.request.dao;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    @Query("SELECT i FROM ItemRequest i WHERE i.requestor.id = :requestor")
    List<ItemRequest> customFindAll(Long requestor);

    List<ItemRequest> findAllByRequestorIdNotOrderByCreatedAsc(Long requestorId, Pageable pageable);

    List<ItemRequest> findAllByRequestorId(Long userId);

    List<ItemRequest> findAllByRequestorIdNot(Long requestorId, Pageable pageable);


}