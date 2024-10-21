package com.omar.ecommerce.item;

import com.omar.ecommerce.exception.ItemPurchaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemConverter itemConverter;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addItem_ShouldReturnItemId() {
        ItemRequest request = new ItemRequest(
                null, "Test Item", "Details", 10, BigDecimal.valueOf(99.99), 1, "SKU123");

        Item item = Item.builder()
                .id(1)
                .title(request.title())
                .details(request.details())
                .stockQuantity(request.stockQuantity())
                .cost(request.cost())
                .sku(request.sku())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        when(itemConverter.convertToItem(request)).thenReturn(item);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Integer itemId = itemService.addItem(request);
        assertEquals(1, itemId);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void getItemById_ShouldReturnItemResponse() {
        Integer itemId = 1;
        Item item = Item.builder().id(itemId).title("Test Item").build();
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(itemConverter.convertToItemResponse(item)).thenReturn(new ItemResponse(
                item.getId(), item.getTitle(), item.getDetails(), item.getStockQuantity(),
                item.getCost(), 1, "Category Title", "Category Details", item.getSku(),
                item.getCreatedAt(), item.getUpdatedAt(), item.isActive()
        ));

        ItemResponse response = itemService.getItemById(itemId);
        assertNotNull(response);
        assertEquals(itemId, response.id());
    }

    @Test
    void buyItems_ShouldThrowItemPurchaseException_WhenItemDoesNotExist() {
        List<ItemPurchaseRequest> purchaseRequests = Arrays.asList(
                new ItemPurchaseRequest(1, 5, "SKU123")
        );

        when(itemRepository.findAllByIdInOrderById(Arrays.asList(1))).thenReturn(Arrays.asList());

        assertThrows(ItemPurchaseException.class, () -> itemService.buyItems(purchaseRequests));
    }
}
