package com.DYShunyaev.LearningWeb.config;

import com.DYShunyaev.LearningWeb.models.Role;
import com.DYShunyaev.LearningWeb.models.User;
import com.DYShunyaev.LearningWeb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//        return new User(user.getUserName(),user.getPassword(), mapRolesToAuthorities(new HashSet<>(user.getRoles())));
          return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles(),username));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles1, String username) {
        List<Role> roles = List.copyOf(roles1);
        User user = userRepository.findByUserName(username).orElseThrow();
        return roles.stream().map(role -> new SimpleGrantedAuthority(String.valueOf(user.getRoles()))).collect(Collectors.toList());
    }
}
