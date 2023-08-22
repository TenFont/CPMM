package dev.tenfont.cpmm.lang;

import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;

public class Literal<T> extends Expression<T> {

    private final T literal;

    public Literal(T literal) {
        this.literal = literal;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        return true;
    }

    @Override
    public T get(Context context) {
        return literal;
    }

    public T get() {
        return literal;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends T> getReturnType() {
        return (Class<? extends T>) literal.getClass();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' + literal + '}';
    }

}
