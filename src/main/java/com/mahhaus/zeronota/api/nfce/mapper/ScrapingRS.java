package com.mahhaus.zeronota.api.nfce.mapper;

import com.mahhaus.zeronota.api.nfce.Produto;
import com.mahhaus.zeronota.util.NumberUtil;
import com.mahhaus.zeronota.util.StringUtils;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.mahhaus.zeronota.util.StringUtils.DATETIME_REGEX;

/**
 * Created by josias.soares on 01/02/2018.
 * 13:02
 */

public class ScrapingRS extends ScrapingBase {
    private static final String NFCE_REGEX = "[0-9]{44}";
    private String chave;

    @Override
    protected void donwloadPage(String pUrl) throws Exception {
        chave = StringUtils.getStringByRegex(urlRequest, NFCE_REGEX);
        String url = "https://www.sefaz.rs.gov.br/ASP/AAE_ROOT/NFE/SAT-WEB-NFE-NFC_2.asp?HML=false&chaveNFe=" +
                chave + "&Action=Avan%E7ar";

        super.donwloadPage(url);
    }

    @Override
    protected void scrapingCabecalho() {
        notaFiscal.setEstabelecimento(document.select("#respostaWS > tbody > tr > td > table > tbody > tr:nth-child(3) > td > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td > table:nth-child(1) > tbody > tr:nth-child(1) > td.NFCCabecalho_SubTitulo").text());
        notaFiscal.setCnpj(document.select("#respostaWS > tbody > tr > td > table > tbody > tr:nth-child(3) > td > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td > table:nth-child(1) > tbody > tr:nth-child(2) > td").text().substring(5, 25).replaceAll("-?[^\\d]", ""));
        notaFiscal.setEndereco(document.select("#respostaWS > tbody > tr > td > table > tbody > tr:nth-child(3) > td > table > tbody > tr > td > table > tbody > tr:nth-child(1) > td > table:nth-child(2) > tbody > tr > td").text());
    }

    @Override
    protected void scrapingProdutos() {
        List<Produto> produtoList = new ArrayList<>();
        int item = 1;
        boolean hasProduct = document.select("#respostaWS > tbody > tr > td > table > tbody > tr:nth-child(3) > td > table > tbody > tr > td > table > tbody > tr:nth-child(5) > td > table > tbody").get(0).children() != null;

        while (hasProduct) {
            hasProduct = document.getElementById("Item + " + item) != null;
            if (hasProduct) {
                Elements elements = document.getElementById("Item + " + item).children();

                Produto produto = new Produto();
                produto.setCodigo(elements.eq(0).text());
                produto.setNome(elements.eq(1).text());
                produto.setQtd(NumberUtil.getDouble(elements.eq(2).text()));
                produto.setUn(elements.eq(3).text());
                produto.setValorUn(NumberUtil.getDouble(elements.eq(4).text()));
                produto.setValorTotal(NumberUtil.getDouble(elements.eq(5).text()));
                produtoList.add(produto);
            }
            item++;
        }

        notaFiscal.setProdutos(produtoList);
    }

    @Override
    protected void scrapingSubTotal() {
        Node totais = document.getElementsByClass("borda-pontilhada-botton").get(3).childNode(1).childNode(1);
        String valorTotal = totais.childNode(0).childNode(3).childNode(0).toString();
        String descontos = totais.childNode(2).childNode(3).childNode(0).toString();
        String formaPagto = totais.childNode(4).childNode(3).childNode(0).toString();

        notaFiscal.setTotalItens((double) notaFiscal.getProdutos().size());
        notaFiscal.setValorAPagar(NumberUtil.getDouble(valorTotal));
        notaFiscal.setValorPagamento(NumberUtil.getDouble(valorTotal) - NumberUtil.getDouble(descontos));
        notaFiscal.setTipoMeioPagamento(formaPagto);
    }

    @Override
    protected void scrapingChave() {
        notaFiscal.setChave(chave);
    }

    @Override
    protected void scrapingDataEmissao() throws ParseException {
        String dataTemp = document.getElementsByClass("NFCCabecalho_SubTitulo").get(2).ownText();
        notaFiscal.setDataEmissao(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(StringUtils.getStringByRegex(dataTemp, DATETIME_REGEX)));
    }

    @Override
    protected void scrapingOutrasInfos() {
    }
}
