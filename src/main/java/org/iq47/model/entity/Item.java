package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Check(constraints = "price > 0")
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    int price;


}
