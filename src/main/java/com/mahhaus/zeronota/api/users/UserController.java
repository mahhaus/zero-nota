package com.mahhaus.zeronota.api.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.processing.SupportedOptions;
import java.net.URI;
import java.util.List;

/**
 * @author josias.soares
 * Create 01/09/2019
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService service;

    @Secured({ "ROLE_ADMIN" })
    @GetMapping()
//    @CrossOrigin(origins = "*")
    public ResponseEntity get() {
        List<UserDTO> list = service.getUsers();
        return ResponseEntity.ok(list);
    }

    @Secured({ "ROLE_ADMIN" })
    @GetMapping("/info")
    public UserDTO userInfo(@AuthenticationPrincipal User user) {

        //UserDetails userDetails = JwtUtil.getUserDetails();

        return UserDTO.create(user);
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody UserDTO userDTO) {
        UserDTO userCreated = service.insert(User.create(userDTO));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userCreated.getId()).toUri();

        //return ResponseEntity.created(location).build();
        return ResponseEntity.ok(userCreated);
    }
}