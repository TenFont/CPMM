package dev.tenfont.cpmm.elements.expressions.binaryexpressions;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.BinaryExpression;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;

public class EqualityExpression extends BinaryExpression {
    private Expression right;

    public EqualityExpression(Expression left) {
        super(left);
    }

    @Override
    public Object get(Context context) {
        return getLeft().get(context).equals(right.get(context));
    }

    @Override
    public boolean init(Parser parser, Context context) {
        parser.eat(TokenType.EQUALITY_OPERATOR);
        right = parser.parseExpression(context);
        return true;
    }
}
