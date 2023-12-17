package org.iq47.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.converter.UserDTOConverter;
import org.iq47.repository.RoleRepository;
import org.iq47.repository.UserRepository;
import org.iq47.model.entity.user.Role;
import org.iq47.model.entity.user.User;
import org.iq47.network.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    private final CartService cartService;

    @Override
    public UserDTO saveUser(UserDTO userDto) {
        String password = userDto.getPassword();
        userDto.setPassword(passwordEncoder.encode(password));
        User userEntity = UserDTOConverter.dtoToEntity(userDto);
        System.out.println(userEntity);
        Set<Role> rolePersistSet = userEntity.getRoleSet().stream()
                .map(rt -> roleRepo.findByRoleName(rt.getRoleName()))
                .collect(Collectors.toSet());
        userEntity.setRoleSet(rolePersistSet);
        User savedEntity = userRepo.save(userEntity);
        cartService.createCart(savedEntity.getUid());
        savedEntity.setPassword(password);
        return UserDTOConverter.entityToDto(savedEntity);
    }

    @Override
    public boolean userExistByName(String username) {
        return (userRepo.findByUsername(username).isPresent());
    }
}
