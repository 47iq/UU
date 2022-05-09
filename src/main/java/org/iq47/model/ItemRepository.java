package org.iq47.model;

import org.iq47.model.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Collection<Item> getItemsByName(String name);
    Item getItemById(long id);
    Collection<Item> getItemsByNameStartsWith(String name);
    Collection<Item> getTop5ItemsByNameContains(String name);
}
