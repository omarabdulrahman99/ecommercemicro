package com.omar.ecommerce.item;

import com.omar.ecommerce.exception.ItemPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    public Integer addItem(ItemRequest request) {
        var item = itemConverter.convertToItem(request);
        item.setCreatedAt(LocalDateTime.now()); // Set creation timestamp
        item.setIsActive(true); // Default to active
        return itemRepository.save(item).getId();
    }

    public ItemResponse getItemById(Integer id) {
        return itemRepository.findById(id)
                .map(itemConverter::convertToItemResponse)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with ID:: " + id));
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(itemConverter::convertToItemResponse)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = ItemPurchaseException.class)
    public List<ItemPurchaseResponse> buyItems(List<ItemPurchaseRequest> request) {
        var itemIds = request.stream()
                .map(ItemPurchaseRequest::itemId)
                .collect(Collectors.toList());

        var availableItems = itemRepository.findAllByIdInOrderById(itemIds);
        if (itemIds.size() != availableItems.size()) {
            throw new ItemPurchaseException("One or more items do not exist");
        }

        var sortedRequests = request.stream()
                .sorted(Comparator.comparing(ItemPurchaseRequest::itemId))
                .collect(Collectors.toList());

        var purchasedItems = new ArrayList<ItemPurchaseResponse>();
        for (int i = 0; i < availableItems.size(); i++) {
            var item = availableItems.get(i);
            var itemRequest = sortedRequests.get(i);
            if (item.getStockQuantity() < itemRequest.quantity()) {
                throw new ItemPurchaseException("Insufficient stock for item with ID:: " + itemRequest.itemId());
            }
            var updatedQuantity = item.getStockQuantity() - itemRequest.quantity();
            item.setStockQuantity(updatedQuantity);
            itemRepository.save(item);
            purchasedItems.add(itemConverter.convertToItemPurchaseResponse(item, itemRequest.quantity()));
        }
        return purchasedItems;
    }
}
