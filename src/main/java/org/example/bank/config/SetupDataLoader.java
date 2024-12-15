package org.example.bank.config;

import jakarta.transaction.Transactional;
import org.example.bank.models.Privilege;
import org.example.bank.models.Role;
import org.example.bank.models.User;
import org.example.bank.repositories.PrivilegeRepository;
import org.example.bank.repositories.RoleRepository;
import org.example.bank.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PrivilegeRepository privilegeRepository;

    public SetupDataLoader(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
        Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User("admin", passwordEncoder.encode("admin"));
        user.setRoles(Arrays.asList(adminRole.get()));
        userRepository.save(user);
        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Optional<Privilege> privilege = privilegeRepository.findByName(name);
        if (privilege.isEmpty()) {
            privilege = Optional.of(new Privilege(name));
            privilegeRepository.save(privilege.get());
        }
        return privilege.orElse(null);
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isEmpty()) {
            role = Optional.of(new Role(name));
            role.get().setPrivileges(privileges);
            roleRepository.save(role.get());
        }
        return role.orElse(null);
    }
}