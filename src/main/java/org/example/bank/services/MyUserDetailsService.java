package org.example.bank.services;

import org.example.bank.models.Privilege;
import org.example.bank.models.Role;
import org.example.bank.models.User;
import org.example.bank.repositories.RoleRepository;
import org.example.bank.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);
    private UserRepository userRepository;
    private MessageSource messages;
    private RoleRepository roleRepository;

    public MyUserDetailsService(UserRepository userRepository,
                                MessageSource messages,
                                RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.messages = messages;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true,
                    true, true, true,
                    getAuthorities(roleRepository.findByName("ROLE_USER").stream().toList()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.get().getName(), user.get().getPassword(),
                true, true, true,
                true, getAuthorities(user.get().getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
