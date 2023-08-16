package dev.tenfont.cpmm.lang;

import dev.tenfont.cpmm.lang.components.Token;
import dev.tenfont.cpmm.lang.components.TokenType;
import dev.tenfont.cpmm.util.Error;
import dev.tenfont.cpmm.util.StringReader;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class LexicalAnalyzer {
    private final StringReader reader;
    private int line = 1, character = 0;
    private Token lookAhead;

    public LexicalAnalyzer(String string) {
        this.reader = new StringReader(string);
    }

    public @Nullable Token getNextToken() {
        if (!hasNextToken()) {
            return (lookAhead = null);
        }

        int start = reader.getCursor();
        for (TokenType type : TokenType.values()) {
            StringReader readerClone = reader.clone();
            Object value = type.getFunction().apply(readerClone);
            if (value == null) continue;
            reader.setCursor(readerClone.getCursor());
            if (type == TokenType.NEW_LINE) {
                line += 1;
                character = 0;
            }
            int prevChar = character;
            character += reader.getCursor() - start;
            if (!type.isSignificant()) return getNextToken();
            return lookAhead = new Token(
                    type,
                    value,
                    reader.getString().substring(start, reader.getCursor()),
                    line,
                    prevChar
            );
        }
        Error.log("Invalid syntax", line, character);
        System.exit(-1);
        return null;
    }

    public boolean hasNextToken() {
        return !reader.isEndOfFile();
    }
}
