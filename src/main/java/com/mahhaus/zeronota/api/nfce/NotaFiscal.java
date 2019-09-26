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
    @Column(length = 500)
    private String urlQrCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "nfce_produto",
            joinColumns = @JoinColumn(name = "notafiscal_chave", referencedColumnName = "chave"),
            inverseJoinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"))
    private List<Produto> produtos;

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj.trim().replaceAll("-?[^\\d]", "");
    }

    public void setTipoMeioPagamento(String tipoMeioPagamento) {
        String tipo = "";
        if (tipoMeioPagamento.toLowerCase().contains("dito"))
            tipo = TipoPagamento.CARTAO_DE_CREDITO.descricao;
        else if (tipoMeioPagamento.toLowerCase().contains("bito"))
            tipo = TipoPagamento.CARTAO_DE_DEBITO.descricao;
        else if (tipoMeioPagamento.toLowerCase().contains("cheq"))
            tipo = TipoPagamento.CHEQUE.descricao;
        else if (tipoMeioPagamento.toLowerCase().contains("inheiro"))
            tipo = TipoPagamento.DINHEIRO.descricao;


        switch (tipoMeioPagamento) {
            case "01":
                tipo = TipoPagamento.DINHEIRO.descricao;
                break;
            case "02":
                tipo = TipoPagamento.CHEQUE.descricao;
                break;
            case "03":
                tipo = TipoPagamento.CARTAO_DE_CREDITO.descricao;
                break;
            case "04":
                tipo = TipoPagamento.CARTAO_DE_DEBITO.descricao;
                break;
            case "05":
                tipo = TipoPagamento.CREDITO_LOJA.descricao;
                break;
            case "10":
                tipo = TipoPagamento.VALE_ALIMENTACAO.descricao;
                break;
            case "11":
                tipo = TipoPagamento.VALE_REFEICAO.descricao;
                break;
            case "12":
                tipo = TipoPagamento.VALE_PRESENTE.descricao;
                break;
            case "13":
                tipo = TipoPagamento.VALE_COMBUSTIVEL.descricao;
                break;
            case "14":
                tipo = TipoPagamento.DUPLICATA_MERCANTIL.descricao;
                break;
            case "15":
                tipo = TipoPagamento.BOLETO_BANCARIO.descricao;
                break;
            case "90":
                tipo = TipoPagamento.SEM_PAGAMENTO.descricao;
                break;
            case "99":
                tipo = TipoPagamento.OUTROS.descricao;
                break;
        }

        if (tipo.isEmpty())
            tipo = TipoPagamento.OUTROS.descricao;

        this.tipoMeioPagamento = tipo;
    }

    private enum TipoPagamento {
        DINHEIRO("Dinheiro"),
        CHEQUE("Cheque"),
        CARTAO_DE_CREDITO("Cartão de Crédito"),
        CARTAO_DE_DEBITO("Cartão de Débito"),
        CREDITO_LOJA("Crédito Loja"),
        VALE_ALIMENTACAO("Vale Alimentação"),
        VALE_REFEICAO("Vale Refeição"),
        VALE_PRESENTE("Vale Presente"),
        VALE_COMBUSTIVEL("Vale Combustível"),
        DUPLICATA_MERCANTIL("Duplicata Mercantil"),
        BOLETO_BANCARIO("Boleto Bancário("),
        SEM_PAGAMENTO("Sem Pagamento"),
        OUTROS("Outros");

        public final String descricao;

        TipoPagamento(String descricao) {
            this.descricao = descricao;
        }
    }
}
