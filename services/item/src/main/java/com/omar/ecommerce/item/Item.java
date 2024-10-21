package com.omar.ecommerce.item;

import com.omar.ecommerce.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "items", schema = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String details;

    @Positive(message = "Stock quantity must be positive")
    private double stockQuantity;

    @Positive(message = "Cost must be positive")
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String sku; // Stock Keeping Unit
    private LocalDateTime createdAt; // Timestamp for when the item was created
    private LocalDateTime updatedAt; // Timestamp for the last update
    private boolean isActive; // Status to indicate if the item is active or discontinued
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
