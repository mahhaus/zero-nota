package com.mahhaus.zeronota.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
@Service
public class UserService {

    @Autowired
    private UserRepository rep;

    public List<UserDTO> getUsers() {
        return rep.findAll().stream().map(UserDTO::create).collect(Collectors.toList());
    }
}