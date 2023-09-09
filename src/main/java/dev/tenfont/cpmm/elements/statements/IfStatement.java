package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Interpreter;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Expression;
import dev.tenfont.cpmm.lang.components.Statement;
import dev.tenfont.cpmm.lang.components.TokenType;

public class IfStatement extends Statement {
    private final BlockStatement blockStatement;
    private Expression test;

    public IfStatement(BlockStatement blockStatement) {
        this.blockStatement = blockStatement;
    }

    @Override
    public void execute(Interpreter interpreter) {
        var value = test.get(interpreter.getCurrentContext());
        if (value != null && (boolean) value) blockStatement.execute(interpreter);
    }

    @Override
    public boolean init(Parser parser, Context context) {
        parser.eat(TokenType.IF);
        parser.eat(TokenType.LEFT_PAREN);
        test = parser.parseExpression(context);
        parser.eat(TokenType.RIGHT_PAREN);
        return true;
    }
}
