package com.dasun.library_manager_core.demo;

import com.dasun.library_manager_core.user.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           GroupRepository groupRepository) {
        return args -> {


            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);

            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);


            Group userGroup = new Group();
            userGroup.setName("user");
            userGroup.getRoles().add(roleUser);
            userGroup.getRoles().add(roleAdmin);

            groupRepository.save(userGroup);

            Group adminGroup = new Group();
            adminGroup.setName("Admin");
            adminGroup.getRoles().add(roleUser);
            adminGroup.getRoles().add(roleAdmin);

            groupRepository.save(adminGroup);

            User john = new User();
            john.setFirstName("John");
            john.setLastName("Doe");
            john.setEmail("john.doe@example.com");
            john.setPassword(passwordEncoder.encode("123"));
            john.getGroups().add(userGroup);
            userRepository.save(john);

            User jane = new User();
            jane.setFirstName("Jane");
            jane.setLastName("Doe");
            jane.setEmail("jane.doe@example.com");
            jane.setPassword(passwordEncoder.encode("123"));
            jane.getGroups().add(adminGroup);
            userRepository.save(jane);
        };
    }
}
