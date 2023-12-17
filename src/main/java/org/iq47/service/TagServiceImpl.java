package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.repository.TagRepository;
import org.iq47.model.entity.item.Tag;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepo;

    public Tag saveRole(Tag tagEntity) {
        return tagRepo.save(tagEntity);
    }

    public void removeRole(Tag tagEntity) {
        tagRepo.delete(tagEntity);
    }
}
