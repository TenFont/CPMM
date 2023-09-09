package dev.tenfont.cpmm.elements.expressions.binaryexpressions;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.BinaryExpression;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
import dev.tenfont.cpmm.util.TypeUtils;

public class RelationalExpression extends BinaryExpression {
    private String operator;
    private Expression right;

    public RelationalExpression(Expression left) {
        super(left);
    }

    @Override
    public Object get(Context context) {
        double leftValue = TypeUtils.toDouble(getLeft().get(context));
        double rightValue = TypeUtils.toDouble(right.get(context));
        return switch (operator) {
            case ">" -> leftValue > rightValue;
            case ">=" -> leftValue >= rightValue;
            case "<" -> leftValue < rightValue;
            case "<=" -> leftValue <= rightValue;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public boolean init(Parser parser, Context context) {
        operator = parser.eat(TokenType.RELATIONAL_OPERATOR, String.class);
        right = parser.parseExpression(context);
        return true;
    }
}
