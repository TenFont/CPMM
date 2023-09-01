package dev.tenfont.cpmm.elements.expressions;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class IdentifierExpression extends Expression<Object> {
    private String identifier;

    @Override
    public @Nullable Object get(Context context) {
        return context.getVariableMap().getVariable(identifier);
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        this.identifier = parser.eat(TokenType.IDENTIFIER, String.class);
        return true;
    }
}
