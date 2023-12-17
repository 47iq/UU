package org.iq47.model.entity.user;

import lombok.Data;
import org.hibernate.annotations.Check;
import org.iq47.model.entity.item.Item;
import org.iq47.model.entity.order.Order;
import org.iq47.model.entity.order.OrderAddress;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Check(constraints = "birthday > '1900-01-01'")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    int uid;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, length = 40)
    String surname = "name";

    @Column(nullable = false, length = 40)
    String name = "name";

    @Column
    Date birthday;

    @OneToMany(mappedBy = "id")
    private List<OrderAddress> orderAddresses;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "favorite_items",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            ))
    private Collection<Role> roleSet;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public User(String username, String password, Collection<Role> roleSet) {
        this.username = username;
        this.password = password;
        this.roleSet = roleSet;
        //todo

    }

    public User() {

    }

    public void addFavoriteItem(Item item) {
        this.items.add(item);
        item.getUsers().add(this);
    }

    public void removeFavoriteItem(long itemId) {
        Item item = this.items.stream().filter(t -> t.getId() == itemId).findFirst().orElse(null);
        if (item != null) {
            this.items.remove(item);
            item.getUsers().remove(this);
        }
    }
}
