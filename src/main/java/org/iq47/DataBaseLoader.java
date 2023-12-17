package org.iq47;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.entity.user.Role;
import org.iq47.model.entity.item.Tag;
import org.iq47.model.entity.item.TagEnum;
import org.iq47.security.userDetails.UserRole;
import org.iq47.service.RoleService;
import org.iq47.service.TagService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@Slf4j
public class DataBaseLoader {

    // saving roles
    @Bean
    CommandLineRunner runRoles(RoleService roleService) {
        return args -> {
            Arrays.stream(UserRole.values()).forEach(role -> roleService.saveRole(new Role(role)));
        };
    }

    @Bean
    CommandLineRunner runTags(TagService tagService) {
        return args -> {
            Arrays.stream(TagEnum.values()).forEach(tag -> tagService.saveRole(new Tag(tag)));
        };
    }
}
