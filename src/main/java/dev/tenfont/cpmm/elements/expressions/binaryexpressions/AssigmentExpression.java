package dev.tenfont.cpmm.elements.expressions.binaryexpressions;

import dev.tenfont.cpmm.elements.expressions.literals.IdentifierExpression;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.*;
import dev.tenfont.cpmm.util.Error;

public class AssigmentExpression extends BinaryExpression {
    private String identifier;
    private Expression value;

    public AssigmentExpression(Expression left) {
        super(left);
    }

    @Override
    public Object get(Context context) {
        var value = this.value.get(context);
        context.getVariableMap().setVariable(identifier, value);
        return value;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        // SYNTAX LOGIC
        var left = getLeft();
        parser.eat(TokenType.ASSIGNMENT_OPERATOR);
        var right = parser.parseExpression(context);

        // Identify which expression (left / right) is the identifier
        if (left instanceof IdentifierExpression) {
            this.identifier = ((IdentifierExpression) left).getIdentifier();
            this.value = right;
        } else if (right instanceof IdentifierExpression) {
            this.identifier = ((IdentifierExpression) right).getIdentifier();
            this.value = left;
        } else {
            var lexer = parser.getLexer();
            Error.log("Non-variable expression cannot be assigned to non-variable expression.", lexer.getLine(), lexer.getCharacter());
            return false;
        }

        // Make sure the identifier is already declared as a variable
        if (!context.getVariableMap().variableExists(identifier)) {
            var lexer = parser.getLexer();
            Error.log("Variable " + identifier + " is assigned but never declared.", lexer.getLine(), lexer.getCharacter());
            return false;
        }
        return true;
    }
}
