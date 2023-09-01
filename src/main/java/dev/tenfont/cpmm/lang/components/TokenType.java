package dev.tenfont.cpmm.lang.components;

import dev.tenfont.cpmm.util.FunctionKeywords;
import dev.tenfont.cpmm.util.StringReader;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum TokenType {

    // WHITESPACE
    NEW_LINE(false, '\n'),
    SPACE(false, ' '),
    TAB(false, '\t'),

    // SYMBOLS
    END_STATEMENT(true, "\uD83D\uDC4D"),
    COMMENT(false, reader -> {
        String s = reader.readUntil(c -> c == '\n');
        return s.endsWith("\uD83D\uDC4E") ? s : null;
    }),
    LEFT_PAREN(true, '('),
    RIGHT_PAREN(true, ')'),
    LEFT_BRACKET(true, '['),
    RIGHT_BRACKET(true, ']'),
    LEFT_CURLY_BRACKET(true, '{'),
    RIGHT_CURLY_BRACKET(true, '}'),
    PERIOD(true, '.'),
    COMMA(true, ','),
    COLON(true, ':'),

    // LITERALS
    BOOLEAN(true, reader -> reader.read("true") ? Boolean.TRUE : reader.read("false") ? Boolean.FALSE : null),
    NUMBER(true, reader -> {
        String number = reader.readUntil(c -> Character.digit(c, 10) == -1 && c != '-' && c != '.');
        try {
            if (number.contains("."))
                return Double.parseDouble(number);
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }),
    STRING(true, reader -> {
        if (!reader.canRead(2) || reader.readChar() != '"')
            return null;
        StringBuilder builder = new StringBuilder();
        boolean escape = false;
        while (!reader.isEndOfFile()) {
            if (escape) {
                escape = false;
                builder.append(reader.readChar());
                continue;
            }
            char peek = reader.peekChar();
            if (peek == '\\') {
                escape = true;
                reader.setCursor(reader.getCursor() + 1);
                continue;
            }
            if (peek == '"') {
                reader.read("\"");
                break;
            }
            builder.append(reader.readChar());
        }
        return builder.toString();
    }),

    // KEYWORDS
    VARIABLE_DECLARATION(true, "var"),
    IF(true, "if"),
    ELSE(true, "else"),
    RETURN(true, "return"),
    FUTURE(true, "future"),
    PAST(true, "past"),
    REVERSE(true, "reverse"),

    // OPERATORS
    ASSIGNMENT_OPERATOR(true, '='),
    ADDITIVE_OPERATOR(true, reader -> {
        char operator = reader.readChar();
        return switch (operator) {
            case '+', '-' -> operator;
            default -> null;
        };
    }),
    COMPARISON_OPERATOR(true, reader -> {
        char operator = reader.readChar();
        if (operator != '>' && operator != '<') return null;
        if (reader.canRead(1) && reader.peekChar() == '=') {
            return operator + reader.readChar();
        }
        return String.valueOf(operator);
    }),

    // FUNCTION
    FUNCTION(true, reader -> {
        String string = reader.readUntil(c -> c == ' ');
        if (!FunctionKeywords.getKeywords().contains(string)) return null;
        return string;
    }),

    // IDENTIFIERS
    IDENTIFIER(true, reader -> reader.readUntil(c -> c == ' ')),

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
