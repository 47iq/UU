package org.iq47.converter;

import org.iq47.model.entity.Role;
import org.iq47.model.entity.User;
import org.iq47.model.entity.item.Item;
import org.iq47.model.entity.item.Tag;
import org.iq47.network.ItemDTO;

import java.util.stream.Collectors;

public class ItemDTOConverter {
    public static Item dtoToEntity(ItemDTO itemDTO, User user) {
        return new Item(itemDTO.getName(), itemDTO.getDescription(), itemDTO.getPrice(), itemDTO.getCoordinatesX(),
                itemDTO.getCoordinatesY(), itemDTO.getTags().stream().map(Tag::new).collect(Collectors.toSet()),
                itemDTO.getImageURL(), user);
    }

    public static ItemDTO entityToDto(Item itemEntity) {
        return ItemDTO.newBuilder()
                .setId(itemEntity.getId())
                .setUserId(itemEntity.getUser().getUid())
                .setName(itemEntity.getName())
                .setDescription(itemEntity.getDescription())
                .setPrice(itemEntity.getPrice())
                .setCoordinatesX(itemEntity.getCoordinatesX())
                .setCoordinatesY(itemEntity.getCoordinatesY())
                .setTags(itemEntity.getTagSet())
                .setImageURL(itemEntity.getImageURL())
                .build();
    }
}
