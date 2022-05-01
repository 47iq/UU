package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.ItemDTOConverter;
import org.iq47.model.UserRepository;
import org.iq47.model.entity.Point;
import org.iq47.model.PointRepository;
import org.iq47.model.entity.User;
import org.iq47.network.ItemDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final PointRepository pointRepo;
    private final UserRepository userRepo;

    @Override
    public Optional<ItemDTO> savePoint(ItemDTO point) {
        //validation
        Optional<User> userOptional = userRepo.findById(point.getUserId());
        if (!userOptional.isPresent()) {
            return Optional.empty();
        }
        Point pointEntity = ItemDTOConverter.dtoToEntity(point, userOptional.get());
        return Optional.of(ItemDTOConverter.entityToDto(pointRepo.save(pointEntity)));
    }

    @Override
    public Collection<ItemDTO> getPointsByUserId(Long userId) {
        return pointRepo.findAllByUserUid(userId).stream()
                .map(ItemDTOConverter::entityToDto).collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDTO> removePointsByUserId(Long userId) {
        Collection<Point> pointDtoCollection = pointRepo.deleteAllByUserUid(userId);
        return pointDtoCollection.stream().map(ItemDTOConverter::entityToDto).collect(Collectors.toList());
    }
}
