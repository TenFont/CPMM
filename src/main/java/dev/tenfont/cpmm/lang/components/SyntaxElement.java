package dev.tenfont.cpmm.lang.components;

import dev.tenfont.cpmm.lang.Parser;

import java.io.Serializable;

public abstract class SyntaxElement implements Serializable {
    public abstract boolean init(Parser parser, Context context);
}
