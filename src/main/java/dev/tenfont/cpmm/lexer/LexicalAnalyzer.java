package dev.tenfont.cpmm.lexer;

import dev.tenfont.cpmm.util.Error;
import dev.tenfont.cpmm.util.StringReader;
import lombok.Getter;

@Getter
public class LexicalAnalyzer {
    private final StringReader reader;
    private int line = 1, character = 0;
    private Token lookAhead;

    public LexicalAnalyzer(String string) {
        this.reader = new StringReader(string);
    }

    public Token getNextToken() {
        if (!hasNextToken()) {
            Error.log("Unexpected end of input", line, character);
            System.exit(-1);
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
                continue;
            }
            character += reader.getCursor() - start;
            if (!type.isSignificant()) continue;
            return lookAhead = new Token(
                    type,
                    value,
                    reader.getString().substring(start, reader.getCursor()),
                    line,
                    character
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
