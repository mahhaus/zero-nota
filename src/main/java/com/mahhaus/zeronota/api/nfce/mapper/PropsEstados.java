package com.mahhaus.zeronota.api.nfce.mapper;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by josias.soares on 01/02/2018.
 * 13:09
 */

public class PropsEstados {
    @Retention(RetentionPolicy.SOURCE)
    public @interface Codigo {
        int RO = 11; //	"Rondônia
        int AC = 12; //	"Acre
        int AM = 13; //	"Amazonas
        int RR = 14; //	"Roraima
        int PA = 15; //	"Pará
        int AP = 16; //	"Amapá
        int TO = 17; //	"Tocantins
        int MA = 21; //	"Maranhão
        int PI = 22; //	"Piauí
        int CE = 23; //	"Ceará
        int RN = 24; //	"Rio Grande do Norte
        int PB = 25; //	"Paraíba
        int PE = 26; //	"Pernambuco
        int AL = 27; //	"Alagoas
        int SE = 28; //	"Sergipe
        int BA = 29; //	"Bahia
        int MG = 31; //	"Minas Gerais
        int ES = 32; //	"Espírito Santo
        int RJ = 33; //	"Rio de Janeiro
        int SP = 35; //	"São Paulo
        int PR = 41; //	"Paraná
        int SC = 42; //	"Santa Catarina
        int RS = 43; //	"Rio Grande do Sul (*)
        int MS = 50; //	"Mato Grosso do Sul
        int MT = 51; //	"Mato Grosso
        int GO = 52; //	"Goiás
        int DF = 53; //	"Distrito Federal
    }

    public @interface Uf {
        String RO = "RO";
        String AC = "AC";
        String AM = "AM";
        String RR = "RR";
        String PA = "PA";
        String AP = "AP";
        String TO = "TO";
        String MA = "MA";
        String PI = "PI";
        String CE = "CE";
        String RN = "RN";
        String PB = "PB";
        String PE = "PE";
        String AL = "AL";
        String SE = "SE";
        String BA = "BA";
        String MG = "MG";
        String ES = "ES";
        String RJ = "RJ";
        String SP = "SP";
        String PR = "PR";
        String SC = "SC";
        String RS = "RS";
        String MS = "MS";
        String MT = "MT";
        String GO = "GO";
        String DF = "DF";
    }

    public @interface Nome {
        String RO = "Rondônia";
        String AC = "Acre";
        String AM = "Amazonas";
        String RR = "Roraima";
        String PA = "Pará";
        String AP = "Amapá";
        String TO = "Tocantins";
        String MA = "Maranhão";
        String PI = "Piauí";
        String CE = "Ceará";
        String RN = "Rio Grande do Norte";
        String PB = "Paraíba";
        String PE = "Pernambuco";
        String AL = "Alagoas";
        String SE = "Sergipe";
        String BA = "Bahia";
        String MG = "Minas Gerais";
        String ES = "Espírito Santo";
        String RJ = "Rio de Janeiro";
        String SP = "São Paulo";
        String PR = "Paraná";
        String SC = "Santa Catarina";
        String RS = "Rio Grande do Sul";
        String MS = "Mato Grosso do Sul";
        String MT = "Mato Grosso";
        String GO = "Goiás";
        String DF = "Distrito Federal";
    }

    public static String getNomeString(@Codigo int aCodigo) {
        switch (aCodigo) {
            case Codigo.RO:
                return Nome.RO;
            case Codigo.AC:
                return Nome.AC;
            case Codigo.AM:
                return Nome.AM;
            case Codigo.RR:
                return Nome.RR;
            case Codigo.PA:
                return Nome.PA;
            case Codigo.AP:
                return Nome.AP;
            case Codigo.TO:
                return Nome.TO;
            case Codigo.MA:
                return Nome.MA;
            case Codigo.PI:
                return Nome.PI;
            case Codigo.CE:
                return Nome.CE;
            case Codigo.RN:
                return Nome.RN;
            case Codigo.PB:
                return Nome.PB;
            case Codigo.PE:
                return Nome.PE;
            case Codigo.AL:
                return Nome.AL;
            case Codigo.SE:
                return Nome.SE;
            case Codigo.BA:
                return Nome.BA;
            case Codigo.MG:
                return Nome.MG;
            case Codigo.ES:
                return Nome.ES;
            case Codigo.RJ:
                return Nome.RJ;
            case Codigo.SP:
                return Nome.SP;
            case Codigo.PR:
                return Nome.PR;
            case Codigo.SC:
                return Nome.SC;
            case Codigo.RS:
                return Nome.RS;
            case Codigo.MS:
                return Nome.MS;
            case Codigo.MT:
                return Nome.MT;
            case Codigo.GO:
                return Nome.GO;
            case Codigo.DF:
                return Nome.DF;
        }
        return "";
    }

    public static String getUfString(@Codigo int aCodigo) {
        switch (aCodigo) {
            case Codigo.RO:
                return Uf.RO;
            case Codigo.AC:
                return Uf.AC;
            case Codigo.AM:
                return Uf.AM;
            case Codigo.RR:
                return Uf.RR;
            case Codigo.PA:
                return Uf.PA;
            case Codigo.AP:
                return Uf.AP;
            case Codigo.TO:
                return Uf.TO;
            case Codigo.MA:
                return Uf.MA;
            case Codigo.PI:
                return Uf.PI;
            case Codigo.CE:
                return Uf.CE;
            case Codigo.RN:
                return Uf.RN;
            case Codigo.PB:
                return Uf.PB;
            case Codigo.PE:
                return Uf.PE;
            case Codigo.AL:
                return Uf.AL;
            case Codigo.SE:
                return Uf.SE;
            case Codigo.BA:
                return Uf.BA;
            case Codigo.MG:
                return Uf.MG;
            case Codigo.ES:
                return Uf.ES;
            case Codigo.RJ:
                return Uf.RJ;
            case Codigo.SP:
                return Uf.SP;
            case Codigo.PR:
                return Uf.PR;
            case Codigo.SC:
                return Uf.SC;
            case Codigo.RS:
                return Uf.RS;
            case Codigo.MS:
                return Uf.MS;
            case Codigo.MT:
                return Uf.MT;
            case Codigo.GO:
                return Uf.GO;
            case Codigo.DF:
                return Uf.DF;
        }
        return "";
    }
}
