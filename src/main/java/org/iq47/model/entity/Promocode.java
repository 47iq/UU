package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.Length;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
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

}
