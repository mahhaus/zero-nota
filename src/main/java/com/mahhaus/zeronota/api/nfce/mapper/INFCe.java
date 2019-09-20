package com.mahhaus.zeronota.api.nfce.mapper;

import com.mahhaus.zeronota.api.nfce.NotaFiscal;

import java.text.ParseException;

/**
 * Created by josias.soares on 01/02/2018.
 * 13:03
 */

public interface INFCe {
    NotaFiscal getNFCe(String url) throws ParseException;
}
