package org.iq47.network;

import lombok.*;
import org.iq47.model.entity.item.Tag;
import org.iq47.model.entity.item.TagEnum;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ItemDTO {

    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private List<String> categories;

    private int price;

}
