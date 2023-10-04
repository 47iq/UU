package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "shop_item_id", nullable = false)
    private ShopItem shopItem;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToMany(mappedBy = "orderItem")
    private List<Review> reviews;

    public OrderItem(ShopItem shopItem, Order order) {
        this.shopItem = shopItem;
        this.order = order;
    }

    //UNIQUE
}
