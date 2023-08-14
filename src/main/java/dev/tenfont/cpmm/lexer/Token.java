package dev.tenfont.cpmm.lexer;

import java.util.Arrays;

public record Token(TokenType type, Object value, String raw, int line, int character) {
    public <T> T value(Class<T> expectedType) {
        return expectedType.cast(value);
    }

    public boolean isType(TokenType... types) {
        return Arrays.stream(types).anyMatch(type -> type == this.type);
    }

    @Override
    public String toString() {
        return "Token[type=" + type + (value == null ? "" : ", value=" + value) +
                ", line=" + line + ", character=" + character + ']';
    }
}
