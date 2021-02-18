package com.example.it_market.service_or_business.impl;

import com.example.it_market.model.User;
import com.example.it_market.model.exception.UserAlreadyExistsException;
import com.example.it_market.model.exception.UserNotFoundException;
import com.example.it_market.persistence_or_repository.UserRepository;
import com.example.it_market.service_or_business.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User registerUser(User user) {
        if (this.userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findById(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }


    @Override
    public void deleteByUsername(String username)
    {
        this.userRepository.deleteById(username);
    }
}
