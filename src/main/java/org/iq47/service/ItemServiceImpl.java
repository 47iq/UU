package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.ItemDTOConverter;
import org.iq47.model.CategoryRepository;
import org.iq47.model.ItemRepository;
import org.iq47.model.TagRepository;
import org.iq47.model.UserRepository;
import org.iq47.model.entity.Category;
import org.iq47.model.entity.Item;
import org.iq47.model.entity.User;
import org.iq47.model.entity.item.Itemm;
import org.iq47.model.entity.item.Tag;
import org.iq47.network.ItemDTO;
import org.iq47.network.request.ItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ResponseWrapper saveItem(long userId, ItemCreateRequest request) {
        User user = userRepository.getById(userId);

        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setCategories(new ArrayList<>());
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

    public Collection<ItemDTO> getItemsByNameStartsWith(String query) {
        Collection<Item> s = itemRepository.getItemsByNameStartsWithIgnoreCase(query);
        return s.stream()
                .map(ItemDTOConverter::entityToDto).collect(Collectors.toList());
    }

    public Optional<ItemDTO> getItemById(long id) {
        Item item = itemRepository.getItemById(id);
        if (item == null) return Optional.empty();
        return Optional.of(ItemDTOConverter.entityToDto(item));
    }

    public Collection<String> getAutocompleteEntries(String query) {
        Collection<Category> tags = categoryRepository.findByNameContainsIgnoreCase(query);
        Collection<Item> items = itemRepository.getTop5ItemsByNameContainsIgnoreCase(query);
        return Stream.concat(tags.stream().map(Category::getName).distinct(),
                items.stream().map(Item::getName).distinct()).collect(Collectors.toList());
    }
}
