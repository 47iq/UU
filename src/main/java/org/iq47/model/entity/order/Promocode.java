package org.iq47.model.entity.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.iq47.model.entity.item.Item;
import org.iq47.model.entity.order.Order;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Check(constraints = "valid_before > '1970-01-01'")
@Table(name = "promo_code")
public class Promocode {
    @Id
    @Column(length = 40, nullable = false)
    String name;

    @Column(nullable = false)
    double discount;

    @Column(nullable = false)
    Date valid_before;

    @OneToMany(mappedBy = "promocode")
    private List<Order> orders;

    @ManyToMany
    private List<Item> items;

}
