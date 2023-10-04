package org.iq47.model.entity.item;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.iq47.security.userDetails.UserRole;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private TagEnum tagName;
    private String name;

    public Tag(TagEnum tagName) {
        this.tagName = tagName;
        name = tagName.name();
    }
}
