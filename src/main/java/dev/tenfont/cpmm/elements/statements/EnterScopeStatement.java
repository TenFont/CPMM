package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Interpreter;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Statement;

public class EnterScopeStatement extends Statement {
    @Override
    public void execute(Interpreter interpreter) {
        interpreter.setCurrentContext(interpreter.getCurrentContext().enterScope());
    }

    @Override
    public boolean init(Parser parser, Context context) {
        return true;
    }
}
