package dev.tenfont.cpmm.lang;

public interface Statement extends SyntaxElement {

    void execute(Context context);

}
