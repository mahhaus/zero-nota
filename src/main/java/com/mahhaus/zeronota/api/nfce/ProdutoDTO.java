package com.mahhaus.zeronota.api.nfce;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by josias.soares on 24/01/2018.
 * 11:18
 */
@Data
public class ProdutoDTO {

    private String id;
    private String nome;
    private String un;
    private Double qtd;
    private Double valorUn;
    private Double valorTotal;
    private String codigo;
    private boolean selecionado;

    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }
}
