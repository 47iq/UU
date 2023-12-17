package org.iq47.model.entity.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.iq47.model.entity.user.Cart;
import org.iq47.model.entity.item.Item;
import org.iq47.model.entity.order.OrderItem;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Check(constraints = "price > 0")
@Table(name = "shop_items")
public class ShopItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @OneToMany(mappedBy = "shopItem")
    private List<OrderItem> orderItems;

    @ManyToMany
    private List<Cart> carts;

}