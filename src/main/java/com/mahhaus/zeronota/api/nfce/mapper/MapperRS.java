package com.mahhaus.zeronota.api.nfce.mapper;

import com.mahhaus.zeronota.api.nfce.NFCe;
import com.mahhaus.zeronota.api.nfce.NotaFiscal;
import com.mahhaus.zeronota.api.nfce.Produto;
import com.mahhaus.zeronota.util.NumberUtil;
import com.mahhaus.zeronota.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mahhaus.zeronota.util.StringUtils.NFCE_REGEX;

/**
 * Created by josias.soares on 01/02/2018.
 * 13:02
 */

public class MapperRS implements INFCe {
    private static final String NFCE_REGEX = "[0-9]{44}";

    private static final String TAG = MapperRS.class.getSimpleName();

    @Override
    public NotaFiscal getNFCe(String pUrl) {

        try {

            String pChave = StringUtils.getStringByRegex(pUrl, NFCE_REGEX);;
            String url = "https://www.sefaz.rs.gov.br/ASP/AAE_ROOT/NFE/SAT-WEB-NFE-NFC_2.asp?HML=false&chaveNFe=" +
                    pChave + "&Action=Avan%E7ar";

            Element conteudo = Jsoup.connect(url.contains("http") ? url : "http://" + url).get().getElementById("respostaWS");

            if (conteudo == null) {
                System.out.println("Não foi possível buscar a Nota Fiscal");
                return null;
            }

            NotaFiscal lNotaFiscal = new NotaFiscal();
            lNotaFiscal.setChave(pChave);

            conteudo.getElementsByTag("tbody").tagName("table").tagName("tbody").tagName("table");

            lNotaFiscal.setEstabelecimento(conteudo.getElementsByClass("NFCCabecalho_SubTitulo").get(0).ownText());
            lNotaFiscal.setCnpj(conteudo.getElementsByClass("NFCCabecalho_SubTitulo1").get(0).ownText().substring(5, 25).replaceAll("-?[^\\d]", ""));
            lNotaFiscal.setEndereco(conteudo.getElementsByClass("NFCCabecalho_SubTitulo1").get(1).ownText());
            String dataTemp = conteudo.getElementsByClass("NFCCabecalho_SubTitulo").get(2).ownText();
            lNotaFiscal.setDataEmissao(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(dataTemp.substring(dataTemp.length() - 19)));

            /* PRODUTOS */
            List<Produto> produtoList = new ArrayList<>();
            boolean hasProduct = true;
            int item = 1;
            while (hasProduct) {
                hasProduct = conteudo.getElementById("Item + " + item) != null;
                if (hasProduct) {
                    Elements elements = conteudo.getElementById("Item + " + item).children();

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


            /* TOTAIS */
            Node totais = conteudo.getElementsByClass("borda-pontilhada-botton").get(3).childNode(1).childNode(1);
            String valorTotal = totais.childNode(0).childNode(3).childNode(0).toString();
            String descontos = totais.childNode(2).childNode(3).childNode(0).toString();
            String formaPagto = totais.childNode(4).childNode(3).childNode(0).toString();

            lNotaFiscal.setValorAPagar(NumberUtil.getDouble(valorTotal));
            lNotaFiscal.setValorPagamento(NumberUtil.getDouble(valorTotal) - NumberUtil.getDouble(descontos) );
            lNotaFiscal.setTotalItens((double) produtoList.size());
            lNotaFiscal.setTipoMeioPagamento(formaPagto);
            lNotaFiscal.setProdutos(produtoList);

            return lNotaFiscal;
        } catch (Exception pE) {
            pE.printStackTrace();
            return null;
        }
    }
}
