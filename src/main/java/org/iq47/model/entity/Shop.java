package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 40, nullable = false)
    String name;

    //@OneToMany(mappedBy = "shop")
    //private List<Role> roles;

    @OneToMany(mappedBy = "shop")
    private List<ShopItem> shopItems;

    public Shop(String shopName) {
        name = shopName;
    }
}
