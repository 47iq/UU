package org.iq47.repository;

import org.iq47.model.entity.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Collection<Item> getItemsByName(String name);
    Item getItemByName(String name);
    Item getItemById(int id);
    Collection<Item> getItemsByNameStartsWithIgnoreCase(String name);
    Collection<Item> getTop5ItemsByNameContainsIgnoreCase(String name);
    @Query(nativeQuery = true, value = "SELECT * from getCatalog(:userId, :itemCount)")
    List<Item> getCatalog(@Param("userId") int userId, @Param("itemCount") int itemCount);

}
