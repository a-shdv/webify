package com.example.webify.services;

import com.example.webify.entities.Role;
import com.example.webify.entities.User;
import com.example.webify.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public void saveUser(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByUsername(principal.getName());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
