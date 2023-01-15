package com.example.AutoVault.controllers;

import com.example.AutoVault.dtos.RoleDto;
import com.example.AutoVault.models.Role;
import com.example.AutoVault.repositories.RoleRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping("/roles")
    public String createRole(@RequestBody RoleDto role) {
        Role newRole = new Role();
        newRole.setRolename(role.rolename);
        roleRepository.save(newRole);
        return "Done";
    }
}
