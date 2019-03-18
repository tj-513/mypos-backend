package com.bootcamp.mypos.mypos.api.item;

import com.bootcamp.mypos.mypos.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findOneByItemName(String itemName);

    List<Item> findByItemName(String itemName);
    List<Item> findByItemNameStartingWith(String itemName);
    List<Item> findByItemNameContaining(String itemName);
}
