package com.mahhaus.zeronota.api.nfce;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by josias.soares on 24/01/2018.
 * 11:18
 */
@Entity
@Data
public class NotaFiscal {
    @Id
    private String chave = UUID.randomUUID().toString();

    private String tipoMeioPagamento;
    private Double valorPagamento;
    private Double valorTributo;
    private Double descontos;
    private Double valorAPagar;
    private String estabelecimento;
    private String cnpj;
    private String endereco;
    private Double totalItens;
    private Date dataEmissao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "nfce_produto",
            joinColumns = @JoinColumn(name = "notafiscal_chave", referencedColumnName = "chave"),
            inverseJoinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"))
    private List<Produto> produtos;

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj.trim().replaceAll("-?[^\\d]", "");
    }

    public void setTipoMeioPagamento(String tipoMeioPagamento) {
        if (tipoMeioPagamento.toLowerCase().contains("dito"))
            tipoMeioPagamento = TipoPagamento.CARTAO_DE_CREDITO.descricao;
        else if (tipoMeioPagamento.toLowerCase().contains("bito"))
            tipoMeioPagamento = TipoPagamento.CARTAO_DE_DEBITO.descricao;
        else if (tipoMeioPagamento.toLowerCase().contains("cheq"))
            tipoMeioPagamento = TipoPagamento.CHEQUE.descricao;
        else tipoMeioPagamento = TipoPagamento.DINHEIRO.descricao;

        this.tipoMeioPagamento = tipoMeioPagamento;
    }

    private enum TipoPagamento {
        CARTAO_DE_CREDITO("Cartão de Crédito"),
        CARTAO_DE_DEBITO("Cartão de Débito"),
        DINHEIRO("Dinheiro"),
        CHEQUE("Cheque");

        public final String descricao;

        TipoPagamento(String descricao) {
            this.descricao = descricao;
        }
    }
}
