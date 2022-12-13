package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.ItemDTOConverter;
import org.iq47.model.*;
import org.iq47.model.entity.Category;
import org.iq47.model.entity.Item;
import org.iq47.model.entity.ShopItem;
import org.iq47.model.entity.User;
import org.iq47.model.entity.item.Itemm;
import org.iq47.model.entity.item.Tag;
import org.iq47.network.ItemDTO;
import org.iq47.network.request.ItemCreateRequest;
import org.iq47.network.response.ItemResponse;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final ShopItemRepository shopItemRepository;

    public ResponseWrapper saveItem(int userId, ItemCreateRequest request) {
        User user = userRepository.getById(userId);

        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setCategories(new ArrayList<>());
        item.setImageURL(request.getImageUrl());
        for (int i = 0; i < request.getCategories().length; i++) {
            if ( ! categoryRepository.existsCategoryByName(request.getCategories()[i])) {
                Category category = new Category();
                category.setName(request.getCategories()[i]);
                categoryRepository.save(category);
                item.getCategories().add(category);
            } else {
                Category category = categoryRepository.getByName(request.getCategories()[i]);
                item.getCategories().add(category);
            }
        }

        itemRepository.save(item);

        return new ResponseWrapper("ok");
    }

    public Set<ItemDTO> getItemsByNameStartsWith(String query) {
        Set<ItemDTO> items = new HashSet<>();

        ArrayList<Item> s = (ArrayList<Item>) itemRepository.getItemsByNameStartsWithIgnoreCase(query);
        ArrayList<Category> tags = (ArrayList<Category>) categoryRepository.findByNameContainsIgnoreCase(query);

        for (Item item : s) {
            ShopItem shopItem = shopItemRepository.getShopItemsByItemOrderByPrice(item);
            if (shopItem != null) {
                items.add(ItemDTOConverter.entityToDto(item, shopItem.getPrice()));
            } else items.add(ItemDTOConverter.entityToDto(item, -1));
        }

        for (Category category : tags) {
            for (Item item : category.getItems()) {
                ShopItem shopItem = shopItemRepository.getShopItemsByItemOrderByPrice(item);
                if (shopItem != null) {
                    items.add(ItemDTOConverter.entityToDto(item, shopItem.getPrice()));
                } else items.add(ItemDTOConverter.entityToDto(item, -1));
            }
        }

        return items;
    }

    public Optional<ItemDTO> getItemById(int id) {
        Item item = itemRepository.getItemById(id);
        if (item == null) return Optional.empty();
        ShopItem shopItem = shopItemRepository.getShopItemsByItemOrderByPrice(item);
        return Optional.of(ItemDTOConverter.entityToDto(item, shopItem.getPrice()));
    }

    public Collection<String> getAutocompleteEntries(String query) {
        Collection<Category> tags = categoryRepository.findByNameContainsIgnoreCase(query);
        Collection<Item> items = itemRepository.getTop5ItemsByNameContainsIgnoreCase(query);
        return Stream.concat(tags.stream().map(Category::getName).distinct(),
                items.stream().map(Item::getName).distinct()).collect(Collectors.toList());
    }

    public ResponseWrapper addFavoriteItem(int userId, int itemId) {
        User user = userRepository.getById(userId);

        user.addFavoriteItem(itemRepository.getItemById(itemId));
        userRepository.save(user);
        return new ResponseWrapper("ok");
    }

    public ResponseWrapper removeFavoriteItem(int userId, int itemId) {
        User user = userRepository.getById(userId);

        user.removeFavoriteItem(itemId);
        userRepository.save(user);
        return new ResponseWrapper("ok");
    }

    public List<ItemDTO> getFavoriteItems(int userId) {
        User user = userRepository.getById(userId);

        return user.getItems().stream().map(ItemDTOConverter::entityToDto).collect(Collectors.toList());
    }
}
