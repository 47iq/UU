package org.iq47.model.entity.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iq47.model.entity.item.Item;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(length = 40, nullable = false)
    String name;

    @ManyToMany(mappedBy = "categories")
    private List<Item> items;
}
