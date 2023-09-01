package dev.tenfont.cpmm.elements.expressions;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.BinaryExpression;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
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
        if (getLeft() instanceof StringLiteralExpression) {
            String left = (String) leftValue;
            return left + rightValue;
        }
        if (!(leftValue instanceof Double) || !(rightValue instanceof Double)) {
            return null;
        }
        return (Double) leftValue + (Double) rightValue;
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
