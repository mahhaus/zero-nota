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

import static com.mahhaus.zeronota.util.StringUtils.DATETIME_REGEX;

/**
 * Created by josias.soares on 01/02/2018.
 * 13:02
 */

public class MapperAM implements INFCe {

    private static final String TAG = MapperAM.class.getSimpleName();

    @Override
    public NotaFiscal getNFCe(String pUrl) {

        try {

            String pChave = NFCe.getChaveByUrl(pUrl);

            Element conteudo = Jsoup.connect(pUrl.contains("http") ? pUrl : "http://" + pUrl).get().getElementById("tbLeiauteDANFENFCe");

            if (conteudo == null) {
                System.out.println("Não foi possível buscar a Nota Fiscal");
                return null;
            }

            NotaFiscal lNotaFiscal = new NotaFiscal();
            lNotaFiscal.setChave(pChave);

            List<Node> nodes = conteudo.getElementById("tbLeiauteDANFENFCe").childNode(0).childNode(0).childNode(0).childNode(2).childNode(0).childNodes();

            // CNPJ
            String cnpj = nodes.get(0).childNode(0).childNode(0).childNode(0).childNode(1).childNode(0).childNode(0).toString().replaceAll("-?[^\\d]", "");
            lNotaFiscal.setCnpj(cnpj);
            // ESTABELECIMENTO
            String estabelecimento = nodes.get(0).childNode(0).childNode(0).childNode(0).childNode(0).childNode(1).childNode(0).toString().trim();
            lNotaFiscal.setEstabelecimento(estabelecimento);
            // ENDERECO
            String endereco = nodes.get(0).childNode(0).childNode(1).childNode(0).childNode(0).childNode(0).childNode(0).toString();
            lNotaFiscal.setEndereco(endereco);
            // DATA DE EMISSAO
            String dataEmissao = nodes.get(5).childNode(0).childNode(0).childNode(0).childNode(2).childNode(0).childNode(0).outerHtml().trim();
            lNotaFiscal.setDataEmissao(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(StringUtils.getStringByRegex(dataEmissao, DATETIME_REGEX)));

            /* PRODUTOS */
            List<Produto> produtoList = new ArrayList<>();
            boolean hasProduct = true;
            int item = 1;
            Element listaProdutos = (Element) nodes.get(2).childNode(0).childNode(0).childNode(0);
            while (hasProduct) {
                hasProduct = listaProdutos.getElementById("Item + " + item) != null;
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
            Node totais = nodes.get(3).childNode(0).childNode(0).childNode(0);

            //String valorTotal = totais.childNode(1).childNode(1).childNode(0).outerHtml();
            //String descontos = totais.childNode(2).childNode(1).outerHtml();
            String formaPagto = totais.childNode(4).childNode(0).childNode(0).outerHtml();
            String valorPago = totais.childNode(4).childNode(1).childNode(0).outerHtml();

            //lNotaFiscal.setDescontos(NumberUtil.getDouble(descontos));
            //lNotaFiscal.setValorAPagar(NumberUtil.getDouble(valorTotal));
            lNotaFiscal.setValorPagamento(NumberUtil.getDouble(valorPago));
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
