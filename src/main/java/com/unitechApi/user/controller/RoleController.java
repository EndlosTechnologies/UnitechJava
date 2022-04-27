package com.unitechApi.user.controller;

import com.unitechApi.user.Repository.RoleRepository;
import com.unitechApi.user.model.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/unitech/api/v1/user/role")
public class RoleController {
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_HR') or hasRole('ROLE_SUBADMIN')")
    public List<Role> FindAll() {
        return roleRepository.findAll();
    }

}
