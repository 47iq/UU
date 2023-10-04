package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.Length;
import org.hibernate.annotations.Check;

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
