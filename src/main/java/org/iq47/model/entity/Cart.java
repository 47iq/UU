package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Check(constraints = "item_count >= 0")
@Table(name = "carts")
public class Cart implements Serializable {

    @Id
    private long user_id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    @MapsId
    private User user;

    @Column(nullable = false)
    private int item_count;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "cart_items",
            joinColumns = @JoinColumn(name = "shop_item_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id")
    )
    private List<ShopItem> shopItem;

    public Cart(User user, int item_count) {
        this.user = user;
        this.item_count = item_count;
    }

    public void addShopItem(ShopItem shopItem) {
        this.shopItem.add(shopItem);
        shopItem.getCarts().add(this);
    }

    public void removeShopItem(long shopItemId) {
        ShopItem shopItem = this.shopItem.stream().filter(t -> t.getId() == shopItemId).findFirst().orElse(null);
        if (shopItem != null) {
            this.shopItem.remove(shopItem);
            shopItem.getCarts().remove(this);
        }
    }
}
