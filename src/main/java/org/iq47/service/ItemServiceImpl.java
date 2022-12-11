package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.ItemDTOConverter;
import org.iq47.model.ItemRepository;
import org.iq47.model.TagRepository;
import org.iq47.model.UserRepository;
import org.iq47.model.entity.User;
import org.iq47.model.entity.item.Itemm;
import org.iq47.model.entity.item.Tag;
import org.iq47.network.ItemDTO;
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

    public Optional<ItemDTO> saveItem(ItemDTO item) {
        Optional<User> userOptional = userRepository.findById(item.getUserId());
        if (!userOptional.isPresent()) {
            return Optional.empty();
        }
        Itemm itemEntity = ItemDTOConverter.dtoToEntity(item, userOptional.get());
        Itemm i = itemRepository.save(itemEntity);
        return Optional.of(ItemDTOConverter.entityToDto(i));
    }

    public Collection<ItemDTO> getItemsByNameStartsWith(String query) {
        Collection<Itemm> s = itemRepository.getItemsByNameStartsWithIgnoreCase(query);
        return s.stream()
                .map(ItemDTOConverter::entityToDto).collect(Collectors.toList());
    }

    public Optional<ItemDTO> getItemById(long id) {
        Itemm item = itemRepository.getItemById(id);
        if (item == null) return Optional.empty();
        return Optional.of(ItemDTOConverter.entityToDto(item));
    }

    public Collection<String> getAutocompleteEntries(String query) {
        Collection<Tag> tags = tagRepository.findByNameContainsIgnoreCase(query);
        Collection<Itemm> items = itemRepository.getTop5ItemsByNameContainsIgnoreCase(query);
        return Stream.concat(tags.stream().map(t -> t.getTagName().name()).distinct(),
                items.stream().map(Itemm::getName).distinct()).collect(Collectors.toList());
    }
}
