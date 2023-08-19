package dev.tenfont.cpmm;

import dev.tenfont.cpmm.lang.Interpreter;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Statement;

import java.io.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please specify a file to execute.");
            System.exit(-1);
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            System.err.println("That file does not exist.");
            System.exit(-1);
        }
        if (!file.getName().endsWith(".cpmm")) {
            System.err.println("Only .cpmm files can be executed.");
            System.exit(-1);
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Parser parser = new Parser(reader.lines().collect(Collectors.joining("\n")));
            long start = System.currentTimeMillis();
            LinkedList<Statement> statements = parser.parse();
            long end = System.currentTimeMillis();
            System.out.println("Successfully parsed in " + (end - start) + "ms");
            new Interpreter(statements).execute();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
