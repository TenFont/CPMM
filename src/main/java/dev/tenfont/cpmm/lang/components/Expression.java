package dev.tenfont.cpmm.lang.components;

import org.jetbrains.annotations.Nullable;

public interface Expression<T> extends SyntaxElement {

    @Nullable T get(Context context);

    Class<? extends T> getReturnType();

}
