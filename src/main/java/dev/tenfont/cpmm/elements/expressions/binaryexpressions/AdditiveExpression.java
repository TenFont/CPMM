package dev.tenfont.cpmm.elements.expressions.binaryexpressions;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.BinaryExpression;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
import dev.tenfont.cpmm.util.TypeUtils;
import org.jetbrains.annotations.Nullable;

public class AdditiveExpression extends BinaryExpression<Object> {
    private Expression<?> right;

    public AdditiveExpression(Expression<?> left) {
        super(left);
    }

    @Override
    public @Nullable Object get(Context context) {
        Object leftValue = getLeft().get(context);
        Object rightValue = right.get(context);
        if (leftValue instanceof String || rightValue instanceof String)
            return TypeUtils.toString(leftValue) + TypeUtils.toString(rightValue);
        return TypeUtils.toDouble(leftValue) + TypeUtils.toDouble(rightValue);
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        parser.eat(TokenType.ADDITIVE_OPERATOR);
        right = parser.parseExpression(context, Object.class);
        return true;
    }
}
