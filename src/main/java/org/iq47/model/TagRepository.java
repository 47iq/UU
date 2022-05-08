package org.iq47.model;

import org.iq47.model.entity.Role;
import org.iq47.model.entity.item.Tag;
import org.iq47.model.entity.item.TagEnum;
import org.iq47.security.userDetails.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByTagName(TagEnum tagName);
    Collection<Tag> findByNameContains(String name);
}
