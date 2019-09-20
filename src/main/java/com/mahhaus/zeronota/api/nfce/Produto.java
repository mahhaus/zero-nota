package com.mahhaus.zeronota.api.nfce;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by josias.soares on 24/01/2018.
 * 11:15
 */
@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String un;
    private Double qtd;
    private Double valorUn;
    private Double valorTotal;
    private String codigo;
    private Boolean selecionado;
}
