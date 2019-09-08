package com.mahhaus.zeronota.api.carro;

import lombok.Data;
import org.modelmapper.ModelMapper;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
@Data
public class CarroDTO {
    private Long id;
    private String nome;
    private String tipo;

    public static CarroDTO create(Carro c){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(c, CarroDTO.class);
    }
}
