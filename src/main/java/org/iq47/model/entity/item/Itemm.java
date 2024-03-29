package org.iq47.model.entity.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iq47.model.entity.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "jlkdfgsashdgklsdafhjgsdfjklghf")
public class Itemm {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "coordinates_x")
    private double coordinatesX;

    @Column(name = "coordinates_y")
    private double coordinatesY;

    //Only PostgreSQL compatible
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "user_uid", referencedColumnName = "uid")
    private User user;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "item_tags",
            joinColumns = @JoinColumn(
                    name = "id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "tag_id",
                    referencedColumnName = "id"
            ))
    private Collection<Tag> tagSet;

    public Collection<TagEnum> getTagSet() {
        return tagSet.stream().map(Tag::getTagName).collect(Collectors.toSet());
    }

    public Itemm(String name, String description, int price, double coordinatesX,
                 double coordinatesY, Collection<Tag> enums, String imageURL, User user) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
        tagSet = enums;
        this.imageURL = imageURL;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itemm item = (Itemm) o;
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
