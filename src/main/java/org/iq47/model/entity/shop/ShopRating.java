package org.iq47.model.entity.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shop_ratings")
public class ShopRating implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column
    private double rating = 0.0;

    @Column
    private int numberOfGrades = 0;
}
