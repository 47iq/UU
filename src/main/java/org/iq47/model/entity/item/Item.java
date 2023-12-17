package org.iq47.model.entity.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iq47.model.entity.order.Promocode;
import org.iq47.model.entity.user.User;
import org.iq47.model.entity.shop.ShopItem;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;


    @OneToMany(mappedBy = "item")
    private List<ShopItem> shopItems;

    @ManyToMany
    @JoinTable(
            name = "item_categories",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_name"),
            indexes = @Index(columnList = "category_name")
    )
    private List<Category> categories;

    @ManyToMany
    private List<User> users;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageURL;

    public Item(String name, String description, List<Category> categories) {
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    @ManyToMany
    @JoinTable(
            name = "item_promo_codes",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "promo_code_name")
    )
    private List<Promocode> promocodes;

}
