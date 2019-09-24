package com.mahhaus.zeronota.api.nfce.mapper;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.nodes.Document;
import org.w3c.dom.NodeList;

/**
 * Created by josias.soares on 01/02/2018.
 * 13:02
 */

public class ScrapingRJ extends ScrapingBase {
    @Override
    protected void donwloadPage(String url) throws Exception {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(url);
            NodeList inputs = page.getElementsByTagName("input");
//            final Iterator<E> nodesIterator = inputs.iterator();
            // now iterate

            document = (Document) inputs;
        }
    }
}
