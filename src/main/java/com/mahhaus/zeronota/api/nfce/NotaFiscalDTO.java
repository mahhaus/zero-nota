package com.mahhaus.zeronota.api.nfce;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by josias.soares on 24/01/2018.
 * 11:18
 */
@Data
public class NotaFiscalDTO {
    private String chave = UUID.randomUUID().toString();

    private String tipoMeioPagamento;
    private Double valorPagamento;
    private Double valorTributo;
    private Double valorAPagar;
    private String estabelecimento;
    private String cnpj;
    private String endereco;
    private Double totalItens;
    private Date dataEmissao;
    private List<ProdutoDTO> produtos;

    public static NotaFiscalDTO create(NotaFiscal notaFiscal) {
        ModelMapper modelMapper = new ModelMapper();
        NotaFiscalDTO dto = modelMapper.map(notaFiscal, NotaFiscalDTO.class);

        dto.produtos = notaFiscal.getProdutos().stream()
                .map(ProdutoDTO::create)
                .collect(Collectors.toList());

        return dto;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }
}
