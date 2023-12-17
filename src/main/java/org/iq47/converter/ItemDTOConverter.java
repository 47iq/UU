package org.iq47.converter;

import org.iq47.model.entity.item.Category;
import org.iq47.model.entity.item.Item;
import org.iq47.model.entity.user.User;
import org.iq47.network.ItemDTO;

import java.util.stream.Collectors;

public class ItemDTOConverter {
    public static ItemDTO entityToDto(Item itemEntity) {
        return new ItemDTO(
                itemEntity.getId(),
                itemEntity.getName(),
                itemEntity.getDescription(),
                itemEntity.getImageURL(),
                itemEntity.getCategories().stream().map(Category::getName).collect(Collectors.toList()),
                -1
        );
    }

    public static ItemDTO entityToDto(Item itemEntity, int price) {
        return new ItemDTO(
                itemEntity.getId(),
                itemEntity.getName(),
                itemEntity.getDescription(),
                itemEntity.getImageURL(),
                itemEntity.getCategories().stream().map(Category::getName).collect(Collectors.toList()),
                price
        );
    }
}
