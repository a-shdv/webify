package com.shadaev.webify.service;

import com.shadaev.webify.entity.User;
import com.shadaev.webify.entity.UserRole;
import com.shadaev.webify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(User userSession) {
        User userFromDb;
        if (userSession != null) {
            userFromDb = findUserByUsername(userSession.getUsername());
            return userFromDb;
        }
        return null;
    }

    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void createUser(User user) {
        user.setActive(true);
        user.setUserRoleSet(Collections.singleton(UserRole.USER));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
