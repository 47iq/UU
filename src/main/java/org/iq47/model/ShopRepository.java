package org.iq47.model;

import org.iq47.model.entity.Shop;
import org.iq47.model.entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Shop getByName(String name);
}
