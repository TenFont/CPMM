package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Interpreter;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Statement;
import dev.tenfont.cpmm.lang.components.TokenType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FunctionDeclarationStatement extends Statement {
    private final BlockStatement body;
    private String identifier;

    @Override
    public void execute(Interpreter interpreter) {
    }

    @Override
    public boolean init(Parser parser, Context context) {
        parser.eat(TokenType.FUNCTION);
        identifier = parser.eat(TokenType.IDENTIFIER, String.class);
        return true;
    }
}
