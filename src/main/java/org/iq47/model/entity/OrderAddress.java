package org.iq47.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Check(constraints = "longitude > -180 AND longitude < 180")
@Table(name = "order_address")
public class OrderAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, length = 40)
    String name;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(nullable = false)
    double longitude;

    @Column(nullable = false)
    double latitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "address")
    private List<Order> orders;
}
