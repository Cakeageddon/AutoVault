package com.example.AutoVault.controllers;

import com.example.AutoVault.dtos.UserDto;
import com.example.AutoVault.models.Role;
import com.example.AutoVault.models.User;
import com.example.AutoVault.repositories.RoleRepository;
import com.example.AutoVault.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @PostMapping("/users")
    public String createUser(@RequestBody UserDto userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.username);
        newUser.setPassword(encoder.encode(userDto.password));

        List<Role> userRoles = new ArrayList<>();
        for (String rolename : userDto.roles) {
            Optional<Role> optionalRole = roleRepository.findById(rolename);
            userRoles.add(optionalRole.get());
        }
        newUser.setRoles(userRoles);
        userRepository.save(newUser);
        return "Done";
    }
}
