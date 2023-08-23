package dev.tenfont.cpmm.elements.expressions;

import dev.tenfont.cpmm.lang.Identifier;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.TokenType;
import dev.tenfont.cpmm.util.Error;

public class AssigmentExpression extends Expression<Object> {

    private final Identifier identifier;
    private Object value;

    public AssigmentExpression(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public Object get(Context context) {
        context.getVariableMap().setVariable(identifier.get(), value);
        return value;
    }

    @Override
    public Class<Object> getReturnType() {
        return Object.class;
    }

    @Override
    public boolean init(Parser parser, Context context) {
        if (!context.getVariableMap().variableExists(identifier.get())) {
            var lexer = parser.getLexer();
            Error.log("Invalid syntax (?) Variable " + identifier.get() + " is assigned but never declared. ", lexer.getLine(), lexer.getCharacter());
            return false;
        }
        parser.eat(TokenType.ASSIGNMENT_OPERATOR);
        value = parser.parseExpression(context, Object.class);
        return true;
    }
}
