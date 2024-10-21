package com.omar.ecommerce.item;

import com.omar.ecommerce.category.Category;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ItemConverter {

    public Item convertToItem(ItemRequest request) {
        return Item.builder()
                .id(request.id())
                .title(request.title())
                .details(request.details())
                .cost(request.cost())
                .stockQuantity(request.stockQuantity())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                ) // Assuming constructor with ID
                .sku(request.sku())
                .createdAt(LocalDateTime.now()) // Set current timestamp
                .isActive(true) // Default to active
                .build();
    }

    public ItemResponse convertToItemResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getTitle(),
                item.getDetails(),
                item.getStockQuantity(),
                item.getCost(),
                item.getCategory().getId(),
                item.getCategory().getName(),
                item.getCategory().getDescription(),
                item.getSku(),
                item.getCreatedAt(),
                item.getUpdatedAt(),
                item.isActive()
        );
    }

    public ItemPurchaseResponse convertToItemPurchaseResponse(Item item, double quantity) {
        return new ItemPurchaseResponse(
                item.getId(),
                item.getTitle(),
                item.getDetails(),
                item.getCost(),
                quantity,
                item.getSku() // Add SKU
        );
    }
}
