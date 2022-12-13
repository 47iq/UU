package org.iq47.model;

import org.iq47.model.entity.Item;
import org.iq47.model.entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ShopItemRepository extends JpaRepository<ShopItem, Integer> {
    Collection<ShopItem> getShopItemsByItem(Item item);

    ShopItem getShopItemsByItemOrderByPrice(Item item);
}
