package com.example.studentManagementSystem.service.serviceImpl;

import com.example.studentManagementSystem.model.Users;
import com.example.studentManagementSystem.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username).
                orElseThrow(()-> new UsernameNotFoundException("Invalid user"));

        return User.withUsername(users.getUsername())
                .password(users.getPassword())
                .disabled(!users.getActive())
                .build();
    }
}
