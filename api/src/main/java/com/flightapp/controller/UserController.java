package com.flightapp.controller;

import com.flightapp.dto.UserDTO;
import com.flightapp.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
//@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    //@PreAuthorize(value = "hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<UserDTO> getUser(@NonNull @PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    //@PreAuthorize(value = "hasAnyAuthority('admin:read', 'user:read')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/allAdmins")
    @PreAuthorize("hasAnyRole('ADMIN')")
    //@PreAuthorize(value = "hasAnyAuthority('admin:read')")
    public ResponseEntity<List<UserDTO>> getAdmins() {
        return ResponseEntity.ok(userService.getAdmins());
    }

    @PostMapping("/createUser")
    @PreAuthorize("hasAnyRole('ADMIN')")
    //@PreAuthorize(value = "hasAnyAuthority('admin:create')")
    public ResponseEntity<UserDTO> createUser(@Valid @NonNull @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    /*@PostMapping("/createAdmin/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    //@PreAuthorize(value = "hasAnyAuthority('admin:create')")
    public ResponseEntity<UserDTO> createAdmin(@NonNull @PathVariable (value = "id") Integer id) {
        return ResponseEntity.ok(userService.createAdmin(id));
    }*/
}
