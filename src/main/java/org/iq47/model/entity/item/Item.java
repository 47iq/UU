package org.iq47.model.entity.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iq47.model.entity.Point;
import org.iq47.model.entity.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

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

    @Column(name = "coordinates_x")
    private double coordinatesX;

    @Column(name = "coordinates_y")
    private double coordinatesY;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_tags",
            joinColumns = @JoinColumn(
                    name = "id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "tag_id",
                    referencedColumnName = "id"
            ))
    private Collection<Tag> tagSet;

    public Item(String name, String description, int price, double coordinatesX, double coordinatesY) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getPrice() == item.getPrice() && Double.compare(item.getCoordinatesX(), getCoordinatesX()) == 0 && Double.compare(item.getCoordinatesY(), getCoordinatesY()) == 0 && Objects.equals(getId(), item.getId()) && Objects.equals(getName(), item.getName()) && Objects.equals(getDescription(), item.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPrice(), getCoordinatesX(), getCoordinatesY());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", coordinatesX=" + coordinatesX +
                ", coordinatesY=" + coordinatesY +
                '}';
    }
}
