package dev.tenfont.cpmm.elements.expressions;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import org.jetbrains.annotations.Nullable;

public class AssignmentExpression extends Expression<Object> {
    @Override
    public @Nullable Object get(Context context) {
        return null;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        return false;
    }
}
