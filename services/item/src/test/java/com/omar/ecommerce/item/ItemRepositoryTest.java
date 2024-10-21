package com.omar.ecommerce.item;

import com.omar.ecommerce.item.ItemRepository;
import com.omar.ecommerce.category.Category; // Import your Category class
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testSaveAndFindItem() {
        // Given
        Category category = new Category(); // Assume you have a valid category
        category.setId(1); // Set a valid ID or mock it if needed

        Item item = Item.builder()
                .title("Test Item")
                .details("This is a test item.")
                .stockQuantity(100)
                .cost(BigDecimal.valueOf(9.99))
                .category(category)
                .sku("SKU123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isActive(true)
                .build();

        // When
        Item savedItem = itemRepository.save(item);

        // Then
        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getId()).isNotNull();

        // When
        Item foundItem = itemRepository.findById(savedItem.getId()).orElse(null);

        // Then
        assertThat(foundItem).isNotNull();
        assertThat(foundItem.getTitle()).isEqualTo("Test Item");
        assertThat(foundItem.getDetails()).isEqualTo("This is a test item.");
        assertThat(foundItem.getStockQuantity()).isEqualTo(100);
        assertThat(foundItem.getCost()).isEqualTo(BigDecimal.valueOf(9.99));
        assertThat(foundItem.getSku()).isEqualTo("SKU123");
        assertThat(foundItem.isActive()).isTrue();
    }

    @Test
    public void testDeleteItem() {
        // Given
        Item item = Item.builder()
                .title("Test Item")
                .details("This is a test item.")
                .stockQuantity(100)
                .cost(BigDecimal.valueOf(9.99))
                .sku("SKU123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isActive(true)
                .build();

        Item savedItem = itemRepository.save(item);

        // When
        itemRepository.delete(savedItem);

        // Then
        assertThat(itemRepository.findById(savedItem.getId())).isEmpty();
    }
}
