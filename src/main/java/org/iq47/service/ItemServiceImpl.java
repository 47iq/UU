package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.ItemDTOConverter;
import org.iq47.model.ItemRepository;
import org.iq47.model.TagRepository;
import org.iq47.model.UserRepository;
import org.iq47.model.entity.Item;
import org.iq47.model.entity.User;
import org.iq47.model.entity.item.Itemm;
import org.iq47.model.entity.item.Tag;
import org.iq47.network.ItemDTO;
import org.iq47.network.request.ItemCreateRequest;
import org.iq47.network.response.ResponseWrapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public ResponseWrapper saveItem(long userId, ItemCreateRequest request) {
        User user = userRepository.getById(userId);

        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
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
        //Collection<Tag> tags = tagRepository.findByNameContainsIgnoreCase(query);
        Collection<Item> items = itemRepository.getTop5ItemsByNameContainsIgnoreCase(query);
        //return Stream.concat(tags.stream().map(t -> t.getTagName().name()).distinct(),
        //        items.stream().map(Itemm::getName).distinct()).collect(Collectors.toList());
        return null;
    }
}
