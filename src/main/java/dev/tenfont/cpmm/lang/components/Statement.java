package dev.tenfont.cpmm.lang.components;

public abstract class Statement extends SyntaxElement {
    public abstract void execute(Context context);
}
