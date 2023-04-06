package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i " +
            "where (lower(i.name) like '%'||lower(:text)||'%' " +
            "or lower(i.description) like '%'||lower(:text)||'%') and i.available = true")
    List<Item> search(@Param("text") String text);

    List<Item> findByOwnerId(Long userId);

    @Query(value = "select i from Item i where Item.owner.id = :userId", nativeQuery = true)
    Set<Item> findByOwnerIdS(Long userId);

    List<Item> findAllByItemRequestId(Long requester);
}
