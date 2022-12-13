package org.iq47.model;

import org.iq47.model.entity.Item;
import org.iq47.model.entity.item.Itemm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Collection<Item> getItemsByName(String name);
    Item getItemByName(String name);
    Item getItemById(int id);
    Collection<Item> getItemsByNameStartsWithIgnoreCase(String name);
    Collection<Item> getTop5ItemsByNameContainsIgnoreCase(String name);


}
