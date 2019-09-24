package com.mahhaus.zeronota.api.nfce.mapper;


import com.mahhaus.zeronota.api.nfce.Produto;
import com.mahhaus.zeronota.util.NumberUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josias.soares on 01/02/2018.
 * 13:02
 */

public class ScrapingPE extends ScrapingBase {

    @Override
    protected void doScraping() throws ParseException {
        notaFiscal.setEstabelecimento(document.select("xNome").text());
        notaFiscal.setCnpj(document.select("emit CNPJ").text());

        String endereco = document.select("enderEmit xLgr").text() + ", " +
                document.select("enderEmit Nro").text() + ", " +
                document.select("enderEmit XBairro").text() + ", " +
                document.select("enderEmit XMun").text() + ", " +
                document.select("enderEmit UF").text();
        notaFiscal.setEndereco(endereco);

        List<Produto> lProdutos = new ArrayList<>();
        document.select("infNFe det").forEach(element -> {
            Produto lProduto = new Produto();
            lProduto.setNome(element.select("prod xProd").text());
            lProduto.setCodigo(element.select("prod cProd").text().replaceAll("\\D+", ""));
            lProduto.setQtd(NumberUtil.getDouble(element.select("prod qCom").text()));
            lProduto.setUn(element.select("prod uTrib").text().trim());
            lProduto.setValorUn(NumberUtil.getDouble(element.select("prod vUnCom").text()));
            lProduto.setValorTotal(NumberUtil.getDouble(element.select("prod vProd").first().ownText()));

            lProdutos.add(lProduto);
        });
        notaFiscal.setProdutos(lProdutos);

        notaFiscal.setTotalItens((double) lProdutos.size());
        notaFiscal.setValorAPagar(NumberUtil.getDouble(document.select("total ICMSTot vNF").text()));
        notaFiscal.setDescontos(NumberUtil.getDouble(document.select("total ICMSTot vDesc").text()));
        notaFiscal.setValorPagamento(NumberUtil.getDouble(document.select("pag vPag").text()));
        notaFiscal.setTipoMeioPagamento(document.select("pag tPag").text());
        Double tributo =
                ( NumberUtil.getDouble(document.select("total ICMSTot vIPI").text()) + NumberUtil.getDouble(document.select("total ICMSTot vPIS").text()) + NumberUtil.getDouble(document.select("total ICMSTot vCOFINS").text()) );
        notaFiscal.setValorTributo(tributo);

        notaFiscal.setChave(document.select("protNFe infProt chNFe").text());
        notaFiscal.setTipoMeioPagamento(document.select("tPag").text().trim());
        notaFiscal.setDataEmissao(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(document.select("dhRecbto").text()));

        notaFiscal.setUrlQrCode(urlRequest);
    }
}
