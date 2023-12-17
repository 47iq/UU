package org.iq47.service;

import org.iq47.network.ItemDTO;
import org.iq47.network.request.ItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.postgresql.util.PSQLException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemService {
    ItemDTO saveItem(int userId, ItemCreateRequest request);
    Set<ItemDTO> getItemsByNameStartsWith(String query);
    Optional<ItemDTO> getItemById(int id);
    Collection<String> getAutocompleteEntries(String query);
    boolean addFavoriteItem(int userId, int itemId);
    boolean removeFavoriteItem(int userId, int itemId);
    List<ItemDTO> getFavoriteItems(int userId);
    List<ItemDTO> getCatalog(int userId, int itemCount) throws PSQLException;
}
