package com.omar.ecommerce.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Integer> createItem(
            @RequestBody @Valid ItemRequest request
    ) {
        return ResponseEntity.ok(itemService.addItem(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ItemPurchaseResponse>> purchaseItems(
            @RequestBody List<ItemPurchaseRequest> request
    ) {
        return ResponseEntity.ok(itemService.buyItems(request));
    }

    @GetMapping("/{item-id}")
    public ResponseEntity<ItemResponse> findById(
            @PathVariable("item-id") Integer itemId
    ) {
        return ResponseEntity.ok(itemService.getItemById(itemId));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAll() {
        return ResponseEntity.ok(itemService.getAllItems());
    }
}
