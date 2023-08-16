package dev.tenfont.cpmm.lang.components;

import dev.tenfont.cpmm.util.StringReader;
import lombok.Getter;

import java.util.function.Function;

@Getter
public enum TokenType {
    // WHITESPACE
    NEW_LINE(false, '\n'),
    SPACE(false, ' '),
    TAB(false, '\t'),

    // KEYWORDS

    IF(true, "if"),
    ELSE(true, "else"),
    RETURN(true, "return"),
    FUTURE(true, "future"),
    PAST(true, "past"),

    // OPERATORS

    ARITHMETIC_OPERATOR(true, reader -> {
        switch (reader.peekChar()) {
            case '+', '-', '/', '%' -> {
                return String.valueOf(reader.readChar());
            }
            case '*' -> {
                reader.readChar();
                if (reader.canRead(1) && reader.peekChar() == '*') {
                    reader.readChar();
                    return "**";
                }
                return "*";
            }
        }
        return null;
    }),
    COMPARISON_OPERATOR(true, reader -> switch (reader.peekChar()) {
        case '>', '<' -> reader.readChar();
        default -> null;
    }),

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

    // IDENTIFIERS

    END_STATEMENT(true, reader -> {
        if (!reader.canRead(2))
            return null;
        return switch (reader.readString(2)) {
            case ":)", "(:" -> 1;
            case ":D", "C:" -> 2;
            default -> null;
        };
    }),
    COMMENT(false, reader -> {
        String s = reader.readUntil(c -> c == '\n');
        return s.endsWith(":(") ? s : null;
    }),
    LEFT_PAREN(true, '('),
    RIGHT_PAREN(true, ')'),
    LEFT_BRACKET(true, '['),
    RIGHT_BRACKET(true, ']'),
    LEFT_CURLY_BRACKET(true, '{'),
    RIGHT_CURLY_BRACKET(true, '}'),
    PERIOD(true, '.'),
    COMMA(true, ','),
    COLON(true, ':')

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
