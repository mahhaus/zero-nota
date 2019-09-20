package com.mahhaus.zeronota.api.nfce;

import com.mahhaus.zeronota.api.nfce.mapper.*;
import com.mahhaus.zeronota.util.NumberUtil;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by josias.soares on 01/02/2018.
 * 13:06
 */

public class NFCe {
    private static final String NFCE_REGEX = "[0-9]{44}";
    private static final int SERVICE_UNAVAILABLE = 503;

    public static void main(String[] args) {
        try {
            // TODO: 14/09/2019 Brasilia nao vem o link vem só os parametros
            //  ?chNFe=53170711759085000102650010000000011000000045&nVersao=100&tpAmb=1&cDest=86346741187&dhEmi=323031372D30372D30375431353A34343A30302D30333A3030&vNF=50.00&vICMS=0.00&digVal=675643374B595566695958375556636C46554D34336F56342F376F3D&cIdToken=&cHashQRCode=358e3bc508ea11032be96e97cd92d4228aea05d0
//            String originalUrl = "http://www.fazenda.df.gov.br/nfce/qrcode?chNFe=53170711759085000102650010000000011000000045&nVersao=100&tpAmb=1&cDest=86346741187&dhEmi=323031372D30372D30375431353A34343A30302D30333A3030&vNF=50.00&vICMS=0.00&digVal=675643374B595566695958375556636C46554D34336F56342F376F3D&cIdToken=&cHashQRCode=358e3bc508ea11032be96e97cd92d4228aea05d0";
            String originalUrl = "http://dec.fazenda.df.gov.br/ConsultarNFCe.aspx?chNFe=53170711759085000102650010000000011000000045&nVersao=100&tpAmb=1&cDest=86346741187&dhEmi=323031372D30372D30375431353A34343A30302D30333A3030&vNF=50.00&vICMS=0.00&digVal=675643374B595566695958375556636C46554D34336F56342F376F3D&cIdToken=&cHashQRCode=358e3bc508ea11032be96e97cd92d4228aea05d0";
//            String originalUrl = "http://dec.fazenda.df.gov.br/ConsultarNFCe.aspx?chNFe=53170800306597005670650390000000101101663095&nVersao=100&tpAmb=2&dhEmi=323031372d30382d30375431303a34313a32342d30333a3030&vNF=10.00&vICMS=0.00&digVal=52497a622f3952625046717a4b77324e4e54644545654e413753553d&cIdToken=000001&cHashQRCode=82060D62CB35C06F264F40E8125152AEE1E34186";

//            String originalUrl = "https://sistemas.sefaz.am.gov.br/nfceweb-hom/consultarNFCe.jsp?chNFe=13160253113791000122650120000000479000000470&nVersao=100&tpAmb=2&cDest=16415434853&dhEmi=323031362d30322d32335430393a30393a34382d30343a3030&vNF=100.00&vICMS=12.00&digVal=74787147546d4670487959527a68464d41345058532f32385565593d&cIdToken=000001&cHashQRCode=AC1478233B8C9AA701FB7B816A7CA6F723516972";
//            aHR0cHM6Ly9zaXN0ZW1hcy5zZWZhei5hbS5nb3YuYnIvbmZjZXdlYi1ob20vY29uc3VsdGFyTkZDZS5qc3A_Y2hORmU9MTMxNjAyNTMxMTM3OTEwMDAxMjI2NTAxMjAwMDAwMDA0NzkwMDAwMDA0NzAmblZlcnNhbz0xMDAmdHBBbWI9MiZjRGVzdD0xNjQxNTQzNDg1MyZkaEVtaT0zMjMwMzEzNjJkMzAzMjJkMzIzMzU0MzAzOTNhMzAzOTNhMzQzODJkMzAzNDNhMzAzMCZ2TkY9MTAwLjAwJnZJQ01TPTEyLjAwJmRpZ1ZhbD03NDc4NzE0NzU0NmQ0NjcwNDg3OTU5NTI3YTY4NDY0ZDQxMzQ1MDU4NTMyZjMyMzg1NTY1NTkzZCZjSWRUb2tlbj0wMDAwMDEmY0hhc2hRUkNvZGU9QUMxNDc4MjMzQjhDOUFBNzAxRkI3QjgxNkE3Q0E2RjcyMzUxNjk3Mg==


//            String originalUrl = "http://www4.fazenda.rj.gov.br/consultaNFCe/QRCode?chNFe=33161209218978000107650010000034591000037428&nVersao=100&tpAmb=1&dhEmi=323031362D31322D32395431373A35343A35372D30323A3030&vNF=32.80&vICMS=0.00&digVal=4231354C61516A2B4A504F625A6741746C6459442F3479665A36633D&cIdToken=000003&cHashQRCode=3604C832A33B8636FC47FEBED0BD796CD456C9FC";
// aHR0cDovL3d3dzQuZmF6ZW5kYS5yai5nb3YuYnIvY29uc3VsdGFORkNlL1FSQ29kZT9jaE5GZT0zMzE2MTIwOTIxODk3ODAwMDEwNzY1MDAxMDAwMDAzNDU5MTAwMDAzNzQyOCZuVmVyc2FvPTEwMCZ0cEFtYj0xJmRoRW1pPTMyMzAzMTM2MkQzMTMyMkQzMjM5NTQzMTM3M0EzNTM0M0EzNTM3MkQzMDMyM0EzMDMwJnZORj0zMi44MCZ2SUNNUz0wLjAwJmRpZ1ZhbD00MjMxMzU0QzYxNTE2QTJCNEE1MDRGNjI1QTY3NDE3NDZDNjQ1OTQ0MkYzNDc5NjY1QTM2NjMzRCZjSWRUb2tlbj0wMDAwMDMmY0hhc2hRUkNvZGU9MzYwNEM4MzJBMzNCODYzNkZDNDdGRUJFRDBCRDc5NkNENDU2QzlGQw==

//            String originalUrl = "https://www.sefaz.rs.gov.br/NFCE/NFCE-COM.aspx?p=43190832066551000144650010000080441240438959|2|1|1|583858703FD802777CC18252F3EF655A77B7E393";
            // aHR0cHM6Ly93d3cuc2VmYXoucnMuZ292LmJyL05GQ0UvTkZDRS1DT00uYXNweD9wPTQzMTkwODMyMDY2NTUxMDAwMTQ0NjUwMDEwMDAwMDgwNDQxMjQwNDM4OTU5fDJ8MXwxfDU4Mzg1ODcwM0ZEODAyNzc3Q0MxODI1MkYzRUY2NTVBNzdCN0UzOTM=

//            String originalUrl = "http://www.dfeportal.fazenda.pr.gov.br/dfe-portal/rest/servico/consultaNFCe?chNFe=41180111237588000118650010001489221001489224&nVersao=100&tpAmb=1&dhEmi=323031382d30312d31305431323a35393a30332d30323a3030&vNF=10.00&vICMS=0.00&digVal=39696b7939316d35305051513032685a74756175737954444835593d&cIdToken=000001&cHashQRCode=E2CD722C5A2C984D685AB1D63B384135E128FFB3";
            // aHR0cDovL3d3dy5kZmVwb3J0YWwuZmF6ZW5kYS5wci5nb3YuYnIvZGZlLXBvcnRhbC9yZXN0L3NlcnZpY28vY29uc3VsdGFORkNlP2NoTkZlPTQxMTgwMTExMjM3NTg4MDAwMTE4NjUwMDEwMDAxNDg5MjIxMDAxNDg5MjI0Jm5WZXJzYW89MTAwJnRwQW1iPTEmZGhFbWk9MzIzMDMxMzgyZDMwMzEyZDMxMzA1NDMxMzIzYTM1MzkzYTMwMzMyZDMwMzIzYTMwMzAmdk5GPTEwLjAwJnZJQ01TPTAuMDAmZGlnVmFsPTM5Njk2Yjc5MzkzMTZkMzUzMDUwNTE1MTMwMzI2ODVhNzQ3NTYxNzU3Mzc5NTQ0NDQ4MzU1OTNkJmNJZFRva2VuPTAwMDAwMSZjSGFzaFFSQ29kZT1FMkNENzIyQzVBMkM5ODRENjg1QUIxRDYzQjM4NDEzNUUxMjhGRkIz

            String encodedUrl = Base64.getUrlEncoder().encodeToString(originalUrl.getBytes());
            System.out.println(encodedUrl);


//            pr : aHR0cDovL3d3dy5kZmVwb3J0YWwuZmF6ZW5kYS5wci5nb3YuYnIvZGZlLXBvcnRhbC9yZXN0L3NlcnZpY28vY29uc3VsdGFORkNlP2NoTkZlPTQxMTgwMTExMjM3NTg4MDAwMTE4NjUwMDEwMDAxNDg5MjIxMDAxNDg5MjI0Jm5WZXJzYW89MTAwJnRwQW1iPTEmZGhFbWk9MzIzMDMxMzgyZDMwMzEyZDMxMzA1NDMxMzIzYTM1MzkzYTMwMzMyZDMwMzIzYTMwMzAmdk5GPTEwLjAwJnZJQ01TPTAuMDAmZGlnVmFsPTM5Njk2Yjc5MzkzMTZkMzUzMDUwNTE1MTMwMzI2ODVhNzQ3NTYxNzU3Mzc5NTQ0NDQ4MzU1OTNkJmNJZFRva2VuPTAwMDAwMSZjSGFzaFFSQ29kZT1FMkNENzIyQzVBMkM5ODRENjg1QUIxRDYzQjM4NDEzNUUxMjhGRkIz
//            String url = "https://www.sefaz.rs.gov.br/NFCE/NFCE-COM.aspx?p=43190832066551000144650010000080441240438959|2|1|1|583858703FD802777CC18252F3EF655A77B7E393";
//
//            Pattern pattern = Pattern.compile(NFCE_REGEX);
//            Matcher matcher = pattern.matcher(url);
//            String chave = "";
//            while (matcher.find()) {
//                chave = matcher.group();
//            }
//
//            NotaFiscal lNotaFiscal = NFCe.getNfce(Integer.parseInt(chave.substring(0, 2)), url);
//
//            String estado = PropsEstados.getNomeString(Integer.parseInt(chave.substring(0, 2)));
//            System.out.println("=============================== "+ estado +" ========================================");
//            System.out.println(lNotaFiscal.toString());
//        } catch (HttpStatusException e) {
//            e.printStackTrace();
//            if (e.getStatusCode() == SERVICE_UNAVAILABLE) {
//                System.out.println("Serviço indisponivel");
//            }
//        } catch (MalformedURLException e) {
//            System.out.println("Url invalida");
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NotaFiscal getNfce(String url) {
        int uf = Integer.parseInt(getChaveByUrl(url).substring(0, 2));

        switch (uf) {
//            case PropsEstados.Codigo.RO:
//                return new MapperRO().getNfce(pDocument);
//            case PropsEstados.Codigo.AC:
//                return new MapperAC().getNfce(pDocument);
            case PropsEstados.Codigo.AM:
                return new MapperAM().getNFCe(url);
//            case PropsEstados.Codigo.RR:
//                return new MapperRR().getNfce(pDocument);
//            case PropsEstados.Codigo.PA:
//            url = "https://appnfc.sefa.pa.gov.br/portal/view/consultas/nfce/nfceForm.seam"+url;
//                return new MapperPA().getNfce(pDocument);
//            case PropsEstados.Codigo.AP:
//                return new MapperAP().getNfce(pDocument);
//            case PropsEstados.Codigo.TO:
//                return new MapperTO().getNfce(pDocument);
//            case PropsEstados.Codigo.MA:
//                return new MapperMA().getNfce(pDocument);
//            case PropsEstados.Codigo.PI:
//                return new MapperPI().getNfce(pDocument);
//            case PropsEstados.Codigo.CE:
//                return new MapperCE().getNfce(pDocument);
//            case PropsEstados.Codigo.RN:
//                return new MapperRN().getNfce(pDocument);
//            case PropsEstados.Codigo.PB:
//                return new MapperPB().getNfce(pDocument);
//            case PropsEstados.Codigo.PE:
//                return new MapperPE().getNfce(pDocument);
//            case PropsEstados.Codigo.AL:
//                return new MapperAL().getNfce(pDocument);
//            case PropsEstados.Codigo.SE:
//                return new MapperSE().getNfce(pDocument);
//            case PropsEstados.Codigo.BA:
//                return new MapperBA().getNfce(pDocument);
//            case PropsEstados.Codigo.MG:
//                return new MapperMG().getNfce(pDocument);
//            case PropsEstados.Codigo.ES:
//                return new MapperES().getNfce(pDocument);
            case PropsEstados.Codigo.RJ:
                return new MapperRJ().getNFCe(url);
//            case PropsEstados.Codigo.SP:
//                return new MapperSP().getNfce(pDocument);
            case PropsEstados.Codigo.PR:
                return new MapperPR().getNFCe(url);
//            case PropsEstados.Codigo.SC:
//                return new MapperSC().getNfce(pDocument);
            case PropsEstados.Codigo.RS:
                return new MapperRS().getNFCe(url);
//            case PropsEstados.Codigo.MS:
//                return new MapperMS().getNfce(pDocument);
//            case PropsEstados.Codigo.MT:
//                return new MapperMT().getNfce(pDocument);
//            case PropsEstados.Codigo.GO:
//                return new MapperGO().getNfce(pDocument);
            case PropsEstados.Codigo.DF:
                url = "http://www.fazenda.df.gov.br/nfce/qrcode" + url;
                return new MapperDF().getNFCe(url);
            default:
                return null;
        }
    }

    public static String getChaveByUrl(String pUrl) {
        Pattern pattern = Pattern.compile(NFCE_REGEX);
        Matcher matcher = pattern.matcher(pUrl);
        String pChave = "";
        while (matcher.find()) {
            pChave = matcher.group();
        }
        return pChave;
    }
}
