package org.iq47.converter;

import org.iq47.model.entity.User;
import org.iq47.model.entity.item.Item;
import org.iq47.network.ItemDTO;

public class ItemDTOConverter {
    public static Item dtoToEntity(ItemDTO itemDTO) {
        return new Item(itemDTO.getName(), itemDTO.getDescription(), itemDTO.getPrice(), itemDTO.getCoordinatesX(),
                itemDTO.getCoordinatesY());
    }

    public static ItemDTO entityToDto(Item itemEntity) {
        return ItemDTO.newBuilder()
                .setName(itemEntity.getName())
                .setDescription(itemEntity.getDescription())
                .setPrice(itemEntity.getPrice())
                .setCoordinatesX(itemEntity.getCoordinatesX())
                .setCoordinatesY(itemEntity.getCoordinatesY())
                .build();
    }
}
