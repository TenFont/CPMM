package dev.tenfont.cpmm.elements.expressions.binaryexpressions;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.BinaryExpression;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
import dev.tenfont.cpmm.util.TypeUtils;
import org.jetbrains.annotations.Nullable;

public class AdditiveExpression extends BinaryExpression {
    private char operator;
    private Expression right;

    public AdditiveExpression(Expression left) {
        super(left);
    }

    @Override
    public @Nullable Object get(Context context) {
        Object leftValue = getLeft().get(context);
        Object rightValue = right.get(context);
        switch (operator) {
            case '+' -> {
                if (leftValue instanceof String || rightValue instanceof String)
                    return TypeUtils.toString(leftValue) + TypeUtils.toString(rightValue);
                return TypeUtils.toDouble(leftValue) + TypeUtils.toDouble(rightValue);
            }
            case '-' -> {
                return TypeUtils.toDouble(leftValue) - TypeUtils.toDouble(rightValue);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean init(Parser parser, Context context) {
        operator = parser.eat(TokenType.ADDITIVE_OPERATOR, Character.class);
        right = parser.parseExpression(context);
        return true;
    }
}
