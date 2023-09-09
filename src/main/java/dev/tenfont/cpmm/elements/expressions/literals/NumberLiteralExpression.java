package dev.tenfont.cpmm.elements.expressions.literals;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
import org.jetbrains.annotations.Nullable;

public class NumberLiteralExpression extends Expression {
    private Double value;

    @Override
    public @Nullable Double get(Context context) {
        return value;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        value = parser.eat(TokenType.NUMBER, Double.class);
        return true;
    }
}
