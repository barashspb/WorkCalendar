package com.example.testauth.services;

import com.example.testauth.domains.User;
import com.example.testauth.misc.Role;
import com.example.testauth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("done!");
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), Collections.singleton(user.getRole())
        );
    }

    //создание профиля пользователя
    public void registerUser(String username, String password, String name,
                             String surname, String email, String work_place) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder().encode(password));
        user.setRole(Role.USER);// Ensure to use a password encoder

        //расширение формы регистрации
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setWork_place(work_place);

        System.out.println(username);
        System.out.println(passwordEncoder().encode(password));

        userRepository.save(user);
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}