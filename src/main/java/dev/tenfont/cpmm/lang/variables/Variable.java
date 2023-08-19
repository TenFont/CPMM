package dev.tenfont.cpmm.lang.variables;

import dev.tenfont.cpmm.lang.Identifier;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.util.ArrayUtils;
import org.jetbrains.annotations.Nullable;

public class Variable<T> extends Expression<T> {

    private final Identifier identifier;
    private final Class<T> superType;
    private final Class<? extends T>[] returnTypes;

    @SuppressWarnings("unchecked")
    public Variable(Identifier identifier, Class<? extends T>[] returnTypes) {
        this.identifier = identifier;
        this.superType = (Class<T>) ArrayUtils.getSuperclass(returnTypes);
        this.returnTypes = returnTypes;
    }

    @Override
    public @Nullable T get(Context context) {
        return context.getVariableMap().getVariable(identifier.get(), returnTypes);
    }

    @Override
    public Class<? extends T> getReturnType() {
        return superType;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        return context.getVariableMap().variableExists(identifier.get());
    }
}
