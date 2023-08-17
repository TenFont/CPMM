package dev.tenfont.cpmm;

import dev.tenfont.cpmm.lang.Interpreter;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Statement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please provide a file to run as the first argument.");
            System.exit(-1);
        }
        File file = new File(args[0]);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Parser parser = new Parser(reader.lines().collect(Collectors.joining("\n")));
            LinkedList<Statement> statements = parser.parse();
//            System.out.println(statements);
            Interpreter interpreter = new Interpreter(statements);
            interpreter.execute();
        } catch (FileNotFoundException e) {
            System.err.println("The file specified does not exist.");
            System.exit(-1);
        }
    }
}
