package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "item_ratings")
public class ItemRating implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column
    private double rating = 0.0;

    @Column
    private int numberOfGrades = 0;
}
