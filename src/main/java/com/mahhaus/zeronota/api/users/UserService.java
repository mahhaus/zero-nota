package com.mahhaus.zeronota.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserDTO> getUsers() {
        return repository.findAll().stream().map(UserDTO::create).collect(Collectors.toList());
    }

    public UserDTO insert(User user) {
        Assert.isNull(user.getId(),"Não foi possível inserir o registro");

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByNome("ROLE_USER"));
        user.setRoles(roles);

        return UserDTO.create(repository.save(user));
    }
}