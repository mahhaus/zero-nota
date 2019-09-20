package com.mahhaus.zeronota.api.nfce.mapper;

import com.mahhaus.zeronota.api.nfce.NotaFiscal;
import com.mahhaus.zeronota.api.nfce.Produto;
import com.mahhaus.zeronota.util.NumberUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josias.soares on 01/02/2018.
 * 13:02
 */

public class MapperSE implements INFCe {

    private static final String TAG = MapperSE.class.getSimpleName();

    @Override
    public NotaFiscal getNFCe(String url) {

        try {
            NotaFiscal lNotaFiscal = new NotaFiscal();

//            Element conteudo = pDocument.body().getElementById("conteudo");
//
//            lNotaFiscal.setChave(pDocument.body().getElementsByClass("chave").first().ownText());
//
//            /* CABEÇALHO */
//            Elements cabecalho = conteudo.getElementsByClass("txtCenter");
//            lNotaFiscal.setEstabelecimento(cabecalho.first().children().get(0).ownText().trim());
//            lNotaFiscal.setCnpj(cabecalho.first().children().get(1).ownText().trim());
//            lNotaFiscal.setEndereco(cabecalho.first().children().get(2).ownText().trim());
//
//            /* ITENS DA NOTA*/
//            Elements lListaItens = conteudo.getElementById("tabResult").getElementsByTag("tbody").first().getAllElements();
//
//            List<Produto> lProdutos = new ArrayList<>();
//            for (Element ln : lListaItens.first().children()) {
//                Produto lProduto = new Produto();
//
//                ln.getElementsByTag("strong").remove();
//
//                lProduto.setNome(ln.getElementsByClass("txtTit").first().ownText().replaceAll("\n", "").trim());
//                lProduto.setQtd(NumberUtil.getDouble(ln.getElementsByClass("Rqtd").first().ownText()));
//                lProduto.setUn(ln.getElementsByClass("RUN").first().ownText().trim());
//                lProduto.setValorUn(NumberUtil.getDouble(ln.getElementsByClass("RvlUnit").first().ownText()));
//                lProduto.setValorTotal(NumberUtil.getDouble(ln.getElementsByClass("valor").first().ownText()));
//                lProduto.setCodigo(ln.getElementsByClass("RCod").first().ownText().trim());
//
//                lProdutos.add(lProduto);
//            }
//
//            lNotaFiscal.setProdutos(lProdutos);
//
//            /* SUBTOTAL DA NOTA */
//            Element totalNota = conteudo.getElementById("totalNota");
//            Elements listItensTotal = totalNota.getAllElements().first().children();
//
//            for (int i = 0; i < listItensTotal.size(); i++) {
//                Element e = listItensTotal.get(i);
//                switch (i) {
//                    case 0:
//                        lNotaFiscal.setTotalItens(NumberUtil.getDouble(e.getElementsByClass("totalNumb").first().ownText()));
//                        break;
//                    case 1:
//                        lNotaFiscal.setValorAPagar(NumberUtil.getDouble(e.getElementsByClass("totalNumb txtMax").first().ownText()));
//                        break;
//                    case 3:
//                        lNotaFiscal.setTipoMeioPagamento(e.getElementsByClass("tx").first().ownText());
//                        lNotaFiscal.setValorPagamento(NumberUtil.getDouble(e.getElementsByClass("totalNumb").first().ownText()));
//                        break;
//                    case 4:
//                        lNotaFiscal.setValorTributo(NumberUtil.getDouble(e.getElementsByClass("totalNumb txtObs").first().ownText()));
//                        break;
//                }
//            }
            return lNotaFiscal;
        } catch (Exception pE) {
            pE.printStackTrace();
            return null;
        }
    }
}
