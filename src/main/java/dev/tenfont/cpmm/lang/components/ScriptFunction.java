package dev.tenfont.cpmm.lang.components;

import dev.tenfont.cpmm.lang.Interpreter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;

@RequiredArgsConstructor
public class ScriptFunction implements Function {
    private final LinkedList<Statement> statements;

    @Override
    public void execute(Object[] arguments) {
        new Interpreter(statements).execute();
    }
}
