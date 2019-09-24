package com.mahhaus.zeronota.util;

import java.text.Normalizer;

/**
 * @author josias.soares
 * Create 08/09/2019
 */
public class NumberUtil {
    public static Double getDouble(String vlr) {
        try {
            return Double.valueOf(Normalizer.normalize(vlr, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "")
                    .replace(",", "."));
        } catch (Exception pE) {
            pE.printStackTrace();
            return 0.0;
        }
    }
}
