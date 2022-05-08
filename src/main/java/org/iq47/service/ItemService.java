package org.iq47.service;

import org.iq47.network.ItemDTO;

import java.util.Collection;
import java.util.Optional;

public interface ItemService {
    Optional<ItemDTO> saveItem(ItemDTO item);
    Collection<ItemDTO> getItemsById(String name);
}
