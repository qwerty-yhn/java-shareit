package ru.practicum.shareit.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;

    private final User user = new User(
            null,
            "Igor",
            "igor@gmail.dom");

    private final ItemRequest request = new ItemRequest(
            null,
            "Запрос",
            user,
            LocalDateTime.now(),
            new ArrayList<>());

    private final Item item = new Item(
            null,
            "name",
            "description",
            true,
            user,
            request);


    @BeforeEach
    void setup() {
        entityManager.persist(user);
        entityManager.persist(request);
        entityManager.flush();
    }

    @Test
    void createItem() {
        Item found = itemRepository.save(item);

        Assertions.assertNotNull(found);
        Assertions.assertEquals(1L, found.getId());
        Assertions.assertEquals(item.getName(), found.getName());
        Assertions.assertEquals(item.getDescription(), found.getDescription());
        Assertions.assertTrue(found.getAvailable());
        Assertions.assertNotNull(found.getItemRequest());
        Assertions.assertEquals(user.getName(), found.getOwner().getName());
        Assertions.assertEquals(user.getEmail(), found.getOwner().getEmail());
    }

    @Test
    void findItemById() {
        entityManager.persist(item);
        entityManager.flush();

        Item found = itemRepository.findById(1L).orElse(null);

        Assertions.assertNotNull(found);
        Assertions.assertEquals(1L, found.getId());
        Assertions.assertEquals(item.getName(), found.getName());
        Assertions.assertEquals(item.getDescription(), found.getDescription());
        Assertions.assertTrue(found.getAvailable());
        Assertions.assertNotNull(found.getItemRequest());
        Assertions.assertEquals(user.getName(), found.getOwner().getName());
        Assertions.assertEquals(user.getEmail(), found.getOwner().getEmail());
    }

    @Test
    void findAllByOwnerIdOrderByIdAsc() {
        entityManager.persist(item);
        entityManager.flush();

        List<Item> found = itemRepository.findByOwnerId(1L);

        Assertions.assertNotNull(found);
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(1L, found.get(0).getId());
        Assertions.assertEquals(item.getName(), found.get(0).getName());
        Assertions.assertEquals(item.getDescription(), found.get(0).getDescription());
        Assertions.assertTrue(found.get(0).getAvailable());
        Assertions.assertNotNull(found.get(0).getItemRequest());
        Assertions.assertEquals(user.getName(), found.get(0).getOwner().getName());
        Assertions.assertEquals(user.getEmail(), found.get(0).getOwner().getEmail());
    }

    @Test
    void search() {
        entityManager.persist(item);
        entityManager.flush();

        List<Item> found = itemRepository.search("name");

        Assertions.assertNotNull(found);
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(1L, found.get(0).getId());
        Assertions.assertEquals(item.getName(), found.get(0).getName());
        Assertions.assertEquals(item.getDescription(), found.get(0).getDescription());
        Assertions.assertTrue(found.get(0).getAvailable());
        Assertions.assertNotNull(found.get(0).getItemRequest());
        Assertions.assertEquals(user.getName(), found.get(0).getOwner().getName());
        Assertions.assertEquals(user.getEmail(), found.get(0).getOwner().getEmail());
    }

    @Test
    void findAllByItemRequestId() {
        entityManager.persist(item);
        entityManager.flush();

        List<Item> found = itemRepository.findAllByItemRequestId(1L);

        Assertions.assertNotNull(found);
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(1L, found.get(0).getId());
        Assertions.assertEquals(item.getName(), found.get(0).getName());
        Assertions.assertEquals(item.getDescription(), found.get(0).getDescription());
        Assertions.assertTrue(found.get(0).getAvailable());
        Assertions.assertNotNull(found.get(0).getItemRequest());
        Assertions.assertEquals(user.getName(), found.get(0).getOwner().getName());
        Assertions.assertEquals(user.getEmail(), found.get(0).getOwner().getEmail());
    }


}
