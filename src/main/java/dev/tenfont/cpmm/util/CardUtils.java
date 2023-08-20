package dev.tenfont.cpmm.util;

import java.util.HashSet;
import java.util.Set;

public class CardUtils {
    static Set<String> set = new HashSet<>();
    static Set<Character> suit = new HashSet<>();

    static {
        set.add("2");
        set.add("3");
        set.add("4");
        set.add("5");
        set.add("6");
        set.add("7");
        set.add("8");
        set.add("9");
        set.add("10");
        set.add("j");
        set.add("q");
        set.add("k");
        set.add("a");

        suit.add('♥');
        suit.add('♣');
        suit.add('♠');
        suit.add('♦');
    }

    public static boolean isCard(String s) {
        return set.contains(s);
    }

    public static boolean isSuit(char c) {
        return suit.contains(c);
    }
}
