package dev.tenfont.cpmm.lang.components;

import dev.tenfont.cpmm.lang.Parser;

public abstract class SyntaxElement {
    public abstract boolean init(Parser parser, Context context);
}
