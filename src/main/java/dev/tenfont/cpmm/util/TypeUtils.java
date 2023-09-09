package dev.tenfont.cpmm.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TypeUtils {
    private static final DecimalFormat decimalFormat;

    static {
        decimalFormat = new DecimalFormat("#.###", new DecimalFormatSymbols(Locale.US));
    }

    public static String toString(Object object) {
        if (object instanceof Double)
            return decimalFormat.format(object);
        return String.valueOf(object);
    }

    public static double toDouble(Object object) {
        if (object instanceof Double)
            return (Double) object;
        else if (object instanceof String)
            return Double.parseDouble(object.toString());
        else if (object instanceof Boolean)
            return ((boolean) object) ? 1d : 0d;
        return 0d;
    }

    public static boolean toBoolean(Object object) {
        if (object instanceof Boolean)
            return (Boolean) object;
        else if (object instanceof Double)
            return (double) object != 0;
        else
            return false;
    }
}
