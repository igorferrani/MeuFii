package com.example.meufii.util;

import java.text.NumberFormat;
import java.util.Locale;

public class UtilFormat {
    public static String formatDecimal(double number) {
        Locale locale = new Locale("pt", "BR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(number);
    }
}
