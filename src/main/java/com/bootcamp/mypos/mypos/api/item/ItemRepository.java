package com.bootcamp.mypos.mypos.api.item;

import com.bootcamp.mypos.mypos.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findOneByItemName(String itemName);
}
