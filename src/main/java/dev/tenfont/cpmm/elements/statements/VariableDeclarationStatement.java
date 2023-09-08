package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Interpreter;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.*;
import dev.tenfont.cpmm.lang.VariableMap;
import dev.tenfont.cpmm.util.Error;

public class VariableDeclarationStatement extends Statement {
    private String identifier;
    private Expression<?> value;

    @Override
    public void execute(Interpreter interpreter) {
        VariableMap map = interpreter.getCurrentContext().getVariableMap();
        map.declareVariable(identifier);
        if (value != null)
            map.setVariable(identifier, value.get(interpreter.getCurrentContext()));
    }

    @Override
    public boolean init(Parser parser, Context context) {
        parser.eat(TokenType.VARIABLE_DECLARATION);
        identifier = parser.eat(TokenType.IDENTIFIER, String.class);
        if (context.getVariableMap().variableExists(identifier)) {
            var lexer = parser.getLexer();
            Error.log("Invalid syntax (?) Variable " + identifier + " is already declared.", lexer.getLine(), lexer.getCharacter());
            return false;
        }
        context.getVariableMap().declareVariable(identifier);
        if (parser.getLexer().hasLookAhead() && parser.getLexer().getLookAhead().type().equals(TokenType.ASSIGNMENT_OPERATOR)) {
            parser.eat(TokenType.ASSIGNMENT_OPERATOR);
            value = parser.parseExpression(context, Object.class);
        }
        parser.eat(TokenType.END_STATEMENT);
        return true;
    }
}
