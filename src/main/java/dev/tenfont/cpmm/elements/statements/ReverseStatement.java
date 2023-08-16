package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Statement;
import dev.tenfont.cpmm.lang.components.TokenType;

public class ReverseStatement extends Statement {
    @Override
    public void execute(Context context) {

    }

    @Override
    public boolean init(Parser parser, Context context) {
        parser.eat(TokenType.REVERSE);
        return true;
    }
}
