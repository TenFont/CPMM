package dev.tenfont.cpmm.util;

public class Error {
    public static void log(String errorMessage, int line, int character) {
        System.err.println("Encountered an error at line " + line + ", character " + character +
                "\n\t" + errorMessage);
    }

    public static void log(String errorMessage, int line) {
        System.err.println("Encountered an error at line " + line +
                "\n\t" + errorMessage);
    }
}
