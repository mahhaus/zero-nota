package com.mahhaus.zeronota.api.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @JsonIgnore
    private Long id;
    // token jwt
    private String token;
    @JsonIgnore
    private List<String> roles;

    private String login;
    private String Senha;
    private String nome;
    private String email;
    private String sexo;
    private Long versionCode;

    public void setSexo(String sexo) {
        this.sexo = sexo != null ? sexo.toUpperCase() : sexo;
    }

    public static UserDTO create(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO dto = modelMapper.map(user, UserDTO.class);

        dto.roles = user.getRoles().stream()
                .map(Role::getNome)
                .collect(Collectors.toList());

        return dto;
    }

    public static UserDTO create(User user, String token) {
        UserDTO dto = create(user);
        dto.token = token;
        return dto;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }
}
