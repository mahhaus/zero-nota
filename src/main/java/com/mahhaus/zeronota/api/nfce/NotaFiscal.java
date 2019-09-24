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
        if (tipoMeioPagamento.toLowerCase().contains("dito"))
            tipoMeioPagamento = TipoPagamento.CARTAO_DE_CREDITO.descricao;
        else if (tipoMeioPagamento.toLowerCase().contains("bito"))
            tipoMeioPagamento = TipoPagamento.CARTAO_DE_DEBITO.descricao;
        else if (tipoMeioPagamento.toLowerCase().contains("cheq"))
            tipoMeioPagamento = TipoPagamento.CHEQUE.descricao;
        else if (tipoMeioPagamento.toLowerCase().contains("inheiro"))
            tipoMeioPagamento = TipoPagamento.DINHEIRO.descricao;


        switch (tipoMeioPagamento) {
            case "01":
                tipoMeioPagamento = TipoPagamento.DINHEIRO.descricao;
                break;
            case "02":
                tipoMeioPagamento = TipoPagamento.CHEQUE.descricao;
                break;
            case "03":
                tipoMeioPagamento = TipoPagamento.CARTAO_DE_CREDITO.descricao;
                break;
            case "04":
                tipoMeioPagamento = TipoPagamento.CARTAO_DE_DEBITO.descricao;
                break;
            case "05":
                tipoMeioPagamento = TipoPagamento.CREDITO_LOJA.descricao;
                break;
            case "10":
                tipoMeioPagamento = TipoPagamento.VALE_ALIMENTACAO.descricao;
                break;
            case "11":
                tipoMeioPagamento = TipoPagamento.VALE_REFEICAO.descricao;
                break;
            case "12":
                tipoMeioPagamento = TipoPagamento.VALE_PRESENTE.descricao;
                break;
            case "13":
                tipoMeioPagamento = TipoPagamento.VALE_COMBUSTIVEL.descricao;
                break;
            case "14":
                tipoMeioPagamento = TipoPagamento.DUPLICATA_MERCANTIL.descricao;
                break;
            case "15":
                tipoMeioPagamento = TipoPagamento.BOLETO_BANCARIO.descricao;
                break;
            case "90":
                tipoMeioPagamento = TipoPagamento.SEM_PAGAMENTO.descricao;
                break;
            case "99":
                tipoMeioPagamento = TipoPagamento.OUTROS.descricao;
                break;
        }

        this.tipoMeioPagamento = tipoMeioPagamento;
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
