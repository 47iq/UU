package org.iq47.repository;

import org.iq47.model.entity.item.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category getByName(String name);
    Collection<Category> findByNameContainsIgnoreCase(String query);
    boolean existsCategoryByName(String name);
}
