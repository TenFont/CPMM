package dev.tenfont.cpmm.elements.expressions;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
import org.jetbrains.annotations.Nullable;

public class    StringLiteralExpression extends Expression<String> {
    private String value;

    @Override
    public @Nullable String get(Context context) {
        return value;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        value = parser.eat(TokenType.STRING, String.class);
        return true;
    }

    @Override
    public String toString() {
        return "StringLiteralExpression[value=" + value + "]";
    }
}
