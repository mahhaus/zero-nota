package com.mahhaus.zeronota.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author josias.soares
 * Create 15/09/2019
 */
public class StringUtils {
    public static final String NFCE_REGEX = "[0-9]{44}";
    public static final String ONLY_NUMBERS = "-?[^\\d]";
    public static final String DATETIME_REGEX = "(\\d{2})/(\\d{2})/(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})";

    public static String getStringByRegex(String text, String regex){
        Pattern p = Pattern.compile(regex);   // the pattern to search for
        Matcher m = p.matcher(text);
        while (m.find()) {
            text = m.group();
        }
        return text;
    }

    public static String getOnlyNumbers(String numbers){
        return numbers.replaceAll("\\D+","");
    }
}
