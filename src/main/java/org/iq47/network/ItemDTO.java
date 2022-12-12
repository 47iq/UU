package org.iq47.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iq47.model.entity.item.Tag;
import org.iq47.model.entity.item.TagEnum;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class ItemDTO {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private List<String> categories;

}
