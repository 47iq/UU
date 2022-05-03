package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.ItemDTOConverter;
import org.iq47.converter.PointDTOConverter;
import org.iq47.model.ItemRepository;
import org.iq47.model.entity.item.Item;
import org.iq47.network.ItemDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    public Optional<ItemDTO> saveItem(ItemDTO item) {
        Item itemEntity = ItemDTOConverter.dtoToEntity(item);
        return Optional.of(ItemDTOConverter.entityToDto(itemRepository.save(itemEntity)));
    }
}
