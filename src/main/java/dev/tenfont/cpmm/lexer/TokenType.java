package dev.tenfont.cpmm.lexer;

import dev.tenfont.cpmm.util.StringReader;
import lombok.Getter;

import java.util.function.Function;

public enum TokenType {
    // WHITESPACE
    NEW_LINE(false, '\n')

    // KEYWORDS

    // OPERATORS

    // LITERALS

    // IDENTIFIERS
    ;
    @Getter
    private final Function<StringReader, Object> function;
    private final boolean significant;

    TokenType(boolean significant, char c) {
        this(significant, reader -> reader.readChar() == c ? c : null);
    }

    TokenType(boolean significant, String s) {
        this(significant, reader -> reader.read(s) ? s : null);
    }

    TokenType(boolean significant, Function<StringReader, Object> function) {
        this.function = function;
        this.significant = significant;
    }

    public boolean isSignificant() {
        return significant;
    }
}
