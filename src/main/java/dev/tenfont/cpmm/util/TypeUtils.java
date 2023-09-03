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
        return object.toString();
    }

    public static Double toDouble(Object object) {
        if (object instanceof Double)
            return (Double) object;
        else if (object instanceof String)
            return Double.parseDouble(object.toString());
        else if (object instanceof Boolean)
            return ((boolean) object) ? 1d : 0d;
        return 0d;
    }
}
