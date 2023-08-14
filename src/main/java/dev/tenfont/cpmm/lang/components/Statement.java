package dev.tenfont.cpmm.lang.components;

public interface Statement extends SyntaxElement {

    void execute(Context context);

}
