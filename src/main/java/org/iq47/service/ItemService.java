package org.iq47.service;

import org.iq47.network.ItemDTO;
import org.iq47.network.request.ItemCreateRequest;
import org.iq47.network.response.ItemResponse;
import org.iq47.network.response.ResponseWrapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemService {
    ResponseWrapper saveItem(int userId, ItemCreateRequest request);
    Set<ItemDTO> getItemsByNameStartsWith(String query);
    Optional<ItemDTO> getItemById(int id);
    Collection<String> getAutocompleteEntries(String query);
    ResponseWrapper addFavoriteItem(int userId, int itemId);
    ResponseWrapper removeFavoriteItem(int userId, int itemId);
    List<ItemDTO> getFavoriteItems(int userId);
}
