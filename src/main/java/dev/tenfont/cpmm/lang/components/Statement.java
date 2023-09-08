package dev.tenfont.cpmm.lang.components;

import dev.tenfont.cpmm.lang.Interpreter;

public abstract class Statement extends SyntaxElement {
    public abstract void execute(Interpreter interpreter);
}
