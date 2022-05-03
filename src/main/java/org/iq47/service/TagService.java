package org.iq47.service;

import org.iq47.model.entity.item.Tag;

public interface TagService {
    Tag saveRole(Tag tagEntity);

    void removeRole(Tag tagEntity);
}
