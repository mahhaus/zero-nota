package com.mahhaus.zeronota.api.nfce.mapper;

import com.mahhaus.zeronota.api.nfce.NFCe;
import com.mahhaus.zeronota.api.nfce.NotaFiscal;
import com.mahhaus.zeronota.api.nfce.Produto;
import com.mahhaus.zeronota.util.NumberUtil;
import com.mahhaus.zeronota.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mahhaus.zeronota.util.StringUtils.DATETIME_REGEX;
import static com.mahhaus.zeronota.util.StringUtils.NFCE_REGEX;

/**
 * @author josias.soares
 * Create 14/09/2019
 */
public class ScrapingBase implements INFCe {
    private enum TipoAmbiente {
        PRODUCAO("1"),
        HOMOLOGACAO("2"),
        CONTIGENCIA("3");

        public final String descricao;

        TipoAmbiente(String descricao) {
            this.descricao = descricao;
        }
    }


    protected NotaFiscal notaFiscal;
    Document document;
    String tipoAmb;
    String urlRequest;

    @Override
    public NotaFiscal getNFCe(String url) {
        try {
            urlRequest = url;
            donwloadPage(url);

            if (document == null) {
                System.out.println("Não foi possível buscar a Nota Fiscal");
                return null;
            }

            notaFiscal = new NotaFiscal();

            doScraping();

            return notaFiscal;

        } catch (Exception pE) {
            pE.printStackTrace();
            return null;
        }
    }

    protected void donwloadPage(String url) throws Exception {
        tipoAmb = StringUtils.getStringByRegex(url, "tpAmb=\\d{1}").replaceAll("\\D+", "");

        if (tipoAmb.equals(TipoAmbiente.HOMOLOGACAO.descricao)) throw new Exception("Nota emitida em homologação.");

        document = Jsoup.connect(url.contains("http") ? url : "http://" + url).timeout(6000).get();
    }

    protected void doScraping() throws ParseException {
        scrapingCabecalho();
        scrapingProdutos();
        scrapingSubTotal();
        scrapingChave();
        scrapingOutrasInfos();
        scrapingDataEmissao();
    }

    protected void scrapingChave() {
//        Elements infos = document.select("div#infos ul");
//        String chave = infos.get(1).select("li").get(0).text().replaceAll("\\D+", "");

        notaFiscal.setChave(StringUtils.getStringByRegex(urlRequest, NFCE_REGEX));
    }

    protected void scrapingCabecalho() {
        Elements cabecalho = document.select("div#conteudo");

        String estabelecimento = cabecalho.select("div.txtCenter div#u20").text();
        String cnpj = cabecalho.select("div.txtCenter div.text").first().text().replaceAll("\\D+", "");
        String endereco = cabecalho.select("div.txtCenter div.text").next().text();
        notaFiscal.setEstabelecimento(estabelecimento);
        notaFiscal.setCnpj(cnpj);
        notaFiscal.setEndereco(endereco);
    }

    protected void scrapingProdutos() {
        Elements itens = document.select("table#tabResult tbody");

        List<Produto> lProdutos = new ArrayList<>();
        itens.forEach(element -> {
            Produto lProduto = new Produto();

            element.getElementsByTag("strong").remove();

            String nome = element.select("tr td span.txtTit2").text();
            lProduto.setNome(nome.isEmpty() ? element.select("tr td span.txtTit").text() : nome);

            lProduto.setCodigo(element.select("tr td span.RCod").text().replaceAll("\\D+", ""));
            lProduto.setQtd(NumberUtil.getDouble(element.select("tr td span.Rqtd").text()));
            lProduto.setUn(element.select("tr td span.RUN").text().trim());
            lProduto.setValorUn(NumberUtil.getDouble(element.select("tr td span.RvlUnit").text()));
            lProduto.setValorTotal(NumberUtil.getDouble(element.select("tr td span.valor").first().ownText()));

            lProdutos.add(lProduto);
        });
        notaFiscal.setProdutos(lProdutos);

    }

    protected void scrapingSubTotal() {
        Elements subtotal = document.select("div#totalNota");

        notaFiscal.setTotalItens(NumberUtil.getDouble(subtotal.select("div#linhaTotal span.totalNumb").first().text()));
        notaFiscal.setValorAPagar(NumberUtil.getDouble(subtotal.select("span.totalNumb.txtMax").text()));
        notaFiscal.setTipoMeioPagamento(subtotal.select("label.tx").text());
        notaFiscal.setValorPagamento(NumberUtil.getDouble(subtotal.select("div.txtRight").first().children().get(3).select("span").text()));
        notaFiscal.setValorTributo(NumberUtil.getDouble(subtotal.select("span.totalNumb.txtObs").text()));

    }

    protected void scrapingDataEmissao() throws ParseException {
        Elements infos = document.select("div#infos ul");

        String dataEmissaoText = StringUtils.getStringByRegex(infos.text(), DATETIME_REGEX);
        Date dataEmissao = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(dataEmissaoText.trim());
        notaFiscal.setDataEmissao(dataEmissao);
    }

    protected void scrapingOutrasInfos() {
        try {
            Elements infos = document.select("div#infos ul");
//            CONSUMIDOR
            String consumidorCPF = infos.get(0).select("li").get(0).text().replaceAll("\\D+", "");

            if (!consumidorCPF.isEmpty()) {
                String consumidorNome = infos.get(0).select("li").get(1).text();
                String consumidorEndereco = infos.get(0).select("li").get(2).text();
            }

//            OUTRAS INFO
            String outrasInformacoes = infos.get(2).select("li").get(0).text();

//            INFORMACOES GERAIS
            String tipoEmissao = infos.get(3).select("li").get(0).children().get(0).text();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
