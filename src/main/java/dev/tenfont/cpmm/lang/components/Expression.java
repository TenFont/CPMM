package dev.tenfont.cpmm.lang.components;

public abstract class Expression extends SyntaxElement {
    public abstract Object get(Context context);
}
