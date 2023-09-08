package dev.tenfont.cpmm.elements.statements;

import dev.tenfont.cpmm.lang.Interpreter;
import dev.tenfont.cpmm.lang.Parser;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Statement;
import dev.tenfont.cpmm.lang.components.TokenType;

import java.util.LinkedList;

public class BlockStatement extends Statement {
    LinkedList<Statement> statements = new LinkedList<>();

    @Override
    public void execute(Interpreter interpreter) {
        interpreter.getStatementList().addAll(interpreter.getIndex(), statements);
    }

    @Override
    public boolean init(Parser parser, Context context) {
        parser.eat(TokenType.LEFT_CURLY_BRACKET);
        statements.add(new EnterScopeStatement());
        statements.addAll(parser.parseStatementList(context.enterScope(), TokenType.RIGHT_CURLY_BRACKET));
        statements.add(new ExitScopeStatement());
        parser.eat(TokenType.RIGHT_CURLY_BRACKET);
        var lexer = parser.getLexer();
        if (lexer.hasLookAhead() && lexer.getLookAhead().isType(TokenType.END_STATEMENT))
            parser.eat(TokenType.END_STATEMENT);
        return true;
    }
}
