package com.shadaev.webify.service;

import com.shadaev.webify.entity.Order;
import com.shadaev.webify.entity.User;
import com.shadaev.webify.entity.UserRole;
import com.shadaev.webify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public void saveUser(User user) {
        user.setActive(true);
        user.setUserRoleSet(Collections.singleton(UserRole.USER));
        userRepository.save(user);
    }

//    public void saveOrder(Order order, User user) {
//        user.getOrderList().add(order);
//        userRepository.save(user);
//    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
