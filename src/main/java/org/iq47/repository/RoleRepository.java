package org.iq47.repository;

import org.iq47.model.entity.user.Role;
import org.iq47.security.userDetails.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(UserRole userRole);
}
