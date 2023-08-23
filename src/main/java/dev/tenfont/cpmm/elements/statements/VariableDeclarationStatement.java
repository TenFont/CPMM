package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Identifier;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Statement;
import dev.tenfont.cpmm.lang.components.TokenType;
import dev.tenfont.cpmm.lang.variables.VariableMap;
import dev.tenfont.cpmm.util.Error;

public class VariableDeclarationStatement extends Statement {

    private final Identifier identifier;
    private Object value;

    public VariableDeclarationStatement(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public void execute(Context context) {
        VariableMap map = context.getVariableMap();
        map.declareVariable(identifier);
        if (value != null)
            map.setVariable(identifier.get(), value);
    }

    @Override
    public boolean init(Parser parser, Context context) {
        if (context.getVariableMap().variableExists(identifier.get())) {
            var lexer = parser.getLexer();
            Error.log("Invalid syntax (?) Variable " + identifier.get() + " is declared but already exists.", lexer.getLine(), lexer.getCharacter());
            return false;
        }
        if (parser.getLexer().hasNextToken() && parser.getLexer().getLookAhead().type().equals(TokenType.ASSIGNMENT_OPERATOR)) {
            parser.eat(TokenType.ASSIGNMENT_OPERATOR);
            value = parser.parseExpression(context, Object.class);
        }

        return true;
    }
}
