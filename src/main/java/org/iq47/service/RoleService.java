package org.iq47.service;

import org.iq47.model.entity.user.Role;

public interface RoleService {
    Role saveRole(Role roleEntity);

    void removeRole(Role roleEntity);
}
