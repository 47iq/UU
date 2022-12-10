package org.iq47.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Check;

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
    Long uid;

    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, length = 40)
    String surname;

    @Column(nullable = false, length = 40)
    String name;

    @Column
    Date birthday;

    @OneToMany(mappedBy = "id")
    private List<OrderAddress> orderAddresses;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

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
}
