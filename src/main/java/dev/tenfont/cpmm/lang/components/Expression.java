package dev.tenfont.cpmm.lang.components;

import org.jetbrains.annotations.Nullable;

public abstract class Expression<T> extends SyntaxElement {
    public abstract @Nullable T get(Context context);

    public abstract Class<? extends T> getReturnType();
}
