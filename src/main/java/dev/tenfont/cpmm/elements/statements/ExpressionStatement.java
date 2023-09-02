package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.Statement;
import dev.tenfont.cpmm.lang.components.TokenType;

public class ExpressionStatement extends Statement {
    private boolean print;
    private Expression<?> expression;

    @Override
    public void execute(Context context) {
        var value = expression.get(context);
        if (print) {
            System.out.println(value);
        }
    }

    @Override
    public boolean init(Parser parser, Context context) {
        print = parser.getLexer().getLookAhead().isType(TokenType.STRING);
        expression = parser.parseExpression(context, Object.class);
        return true;
    }

    @Override
    public String toString() {
        return "ExpressionStatement[expression=" + expression + "]";
    }
}
