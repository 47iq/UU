package org.iq47.service;

import org.iq47.network.ItemDTO;

import java.util.Collection;
import java.util.Optional;

public interface ItemService {
    Optional<ItemDTO> savePoint(ItemDTO point);

    Collection<ItemDTO> getPointsByUserId(Long userId);

    Collection<ItemDTO> removePointsByUserId(Long userId);
}
