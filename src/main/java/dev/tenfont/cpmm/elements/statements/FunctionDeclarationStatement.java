package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Interpreter;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Statement;
import dev.tenfont.cpmm.lang.components.TokenType;

public class FunctionDeclarationStatement extends Statement {
    @Override
    public void execute(Interpreter interpreter) {
        System.out.println("Function declaration statement executing test.");
    }

    @Override
    public boolean init(Parser parser, Context context) {
        parser.eat(TokenType.FUNCTION);
        parser.eat(TokenType.END_STATEMENT);
        return true;
    }
}
