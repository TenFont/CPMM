package dev.tenfont.cpmm.elements.expressions.literals;

import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
import dev.tenfont.cpmm.util.Error;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class IdentifierExpression extends Expression {
    // Store the line and character in case the statement is preloaded & value is null
    private int line, character;
    private String identifier;

    @Override
    public @Nullable Object get(Context context) {
        if (!context.getVariableMap().variableExists(identifier)) {
            Error.log("Variable accession and declaration must be executed concurrently. One is preloaded while the other is not.", line, character);
            return null;
        }
        return context.getVariableMap().getVariable(identifier);
    }

    @Override
    public boolean init(Parser parser, Context context) {
        var token = parser.eat(TokenType.IDENTIFIER);
        line = token.line();
        character = token.character();
        identifier = (String) token.value();
        if (!context.getVariableMap().variableExists(identifier)) {
            var lexer = parser.getLexer();
            Error.log("Variable " + identifier + " is never declared.", lexer.getLine(), lexer.getCharacter());
            return false;
        }
        return true;
    }
}
