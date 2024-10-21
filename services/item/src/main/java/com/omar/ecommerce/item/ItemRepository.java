package com.omar.ecommerce.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByIdInOrderById(List<Integer> ids);
}
