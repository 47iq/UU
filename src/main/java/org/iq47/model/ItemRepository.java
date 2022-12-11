package org.iq47.model;

import org.iq47.model.entity.item.Itemm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Itemm, Long> {
    Collection<Itemm> getItemsByName(String name);
    Itemm getItemById(long id);
    Collection<Itemm> getItemsByNameStartsWithIgnoreCase(String name);
    Collection<Itemm> getTop5ItemsByNameContainsIgnoreCase(String name);
}
