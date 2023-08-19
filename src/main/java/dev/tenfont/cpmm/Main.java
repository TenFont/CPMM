package dev.tenfont.cpmm;

import dev.tenfont.cpmm.lang.Interpreter;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Statement;

import java.io.*;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Please provide a goal and file to run as the arguments.\n" +
                    "Arguments: <parse/execute> <filePath>");
            System.exit(-1);
        }
        File file = new File(args[1]);
        if (!file.exists()) {
            System.err.println("That file does not exist.");
            System.exit(-1);
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            switch (args[0]) {
                case "parse" -> {
                    if (!file.getName().endsWith(".cpmm")) {
                        System.err.println("Only .cpmm files can be parsed.");
                        System.exit(-1);
                    }
                    Parser parser = new Parser(reader.lines().collect(Collectors.joining("\n")));
                    long start = System.currentTimeMillis();
                    LinkedList<Statement> statements = parser.parse();
                    long end = System.currentTimeMillis();
                    System.out.println("Successfully parsed in " + (end - start) + "ms");
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file.getName().replaceFirst("\\.[^.]+$", "") + ".mmpc"));
                    outputStream.writeObject(statements);
                }
                case "execute" -> {
                    if (!file.getName().endsWith(".mmpc")) {
                        System.err.println("Only parsed cpmm (.mmpc) files can be executed.");
                        System.exit(-1);
                    }
                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                    LinkedList<Statement> statements = (LinkedList<Statement>) inputStream.readObject();
                    new Interpreter(statements).execute();
                }
                default -> System.err.println("No goal was provided.\n" +
                        "\"Arguments: <parse/run> <filePath>\"");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
