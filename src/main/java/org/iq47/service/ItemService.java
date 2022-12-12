package org.iq47.service;

import org.iq47.network.ItemDTO;
import org.iq47.network.request.ItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;

import java.util.Collection;
import java.util.Optional;

public interface ItemService {
    ResponseWrapper saveItem(long userId, ItemCreateRequest request);
    Collection<ItemDTO> getItemsByNameStartsWith(String name);
    Optional<ItemDTO> getItemById(long id);
    Collection<String> getAutocompleteEntries(String query);
    ResponseWrapper addFavoriteItem(long userId, long itemId);
    ResponseWrapper removeFavoriteItem(long userId, long itemId);
}
