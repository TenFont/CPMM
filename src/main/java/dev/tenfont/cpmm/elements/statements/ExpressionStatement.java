package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.Statement;

public class ExpressionStatement extends Statement {
    private Expression<?> expression;

    @Override
    public void execute(Context context) {
        var value = expression.get(context);
        if (value instanceof String) {
            System.out.println(value);
        }
    }

    @Override
    public boolean init(Parser parser, Context context) {
        expression = parser.parseExpression(context, Object.class);
        return true;
    }

    @Override
    public String toString() {
        return "ExpressionStatement[expression=" + expression + "]";
    }
}
