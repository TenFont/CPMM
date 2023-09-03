package dev.tenfont.cpmm.elements.expressions.literals;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
import org.jetbrains.annotations.Nullable;

public class BooleanLiteralExpression extends Expression<Boolean> {
    boolean value;

    @Override
    public @Nullable Boolean get(Context context) {
        return value;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        value = parser.eat(TokenType.BOOLEAN, Boolean.class);
        return true;
    }
}
