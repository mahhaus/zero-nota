package com.mahhaus.zeronota.api.carro;


import lombok.*;

import javax.persistence.*;

/**
 * @author josias.soares
 * Create 02/09/2019
 */
@Entity//(name = "carro")
@Data
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nome;
    private String tipo;
    private String descricao;
    private String url_foto;
    private String url_video;
    private String latitude;
    private String longitude;
    private String tip;
}
