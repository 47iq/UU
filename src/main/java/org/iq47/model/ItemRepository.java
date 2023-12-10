package org.iq47.model;

import org.iq47.model.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item getItemById(long id);
    Collection<Item> getItemsByNameStartsWithIgnoreCase(String name);
    Collection<Item> getTop5ItemsByNameContainsIgnoreCase(String name);
}
