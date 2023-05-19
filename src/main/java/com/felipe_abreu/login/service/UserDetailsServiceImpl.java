package com.felipe_abreu.login.service;

import com.felipe_abreu.login.model.UserModel;
import com.felipe_abreu.login.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário [" + username + "] não encontrado");
        }
        User user1 = new User(user.getLogin(), user.getPassword(), user.getAuthorities());
        return user1;
    }
}
