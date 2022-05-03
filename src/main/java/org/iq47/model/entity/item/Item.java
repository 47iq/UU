package org.iq47.model.entity.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iq47.model.entity.Point;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_tags",
            joinColumns = @JoinColumn(
                    name = "item_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "tag_id",
                    referencedColumnName = "id"
            ))
    private Collection<Tag> roleSet;

    @Column(name = "coordinates_x")
    private double coordinatesX;

    @Column(name = "coordinates_y")
    private double coordinatesY;
}
