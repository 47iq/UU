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
@Table(name = "categories")
public class Category {
    @Id
    @Column(length = 40, nullable = false)
    String name;

    @ManyToMany(mappedBy = "categories")
    private List<Item> items;
}
