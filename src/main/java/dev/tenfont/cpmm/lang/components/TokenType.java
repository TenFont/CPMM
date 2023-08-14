package dev.tenfont.cpmm.lang.components;

import dev.tenfont.cpmm.util.StringReader;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum TokenType {
    // WHITESPACE
    NEW_LINE(false, '\n')

    // KEYWORDS

    // OPERATORS

    // LITERALS

    // IDENTIFIERS
    ;

    private final boolean significant;
    private final Function<StringReader, Object> function;

    TokenType(boolean significant, char c) {
        this(significant, reader -> reader.readChar() == c ? c : null);
    }

    TokenType(boolean significant, String s) {
        this(significant, reader -> reader.read(s) ? s : null);
    }

    TokenType(boolean significant, Function<StringReader, Object> function) {
        this.significant = significant;
        this.function = function;
    }
}
