package dev.tenfont.cpmm.lang;

import dev.tenfont.cpmm.elements.expressions.binaryexpressions.*;
import dev.tenfont.cpmm.elements.expressions.literals.BooleanLiteralExpression;
import dev.tenfont.cpmm.elements.expressions.literals.IdentifierExpression;
import dev.tenfont.cpmm.elements.expressions.literals.NumberLiteralExpression;
import dev.tenfont.cpmm.elements.expressions.literals.StringLiteralExpression;
import dev.tenfont.cpmm.elements.statements.*;
import dev.tenfont.cpmm.lang.components.*;
import dev.tenfont.cpmm.util.Error;
import lombok.Getter;

import java.util.LinkedList;

@Getter
public class Parser {
    private final LexicalAnalyzer lexer;
    private final String string;
    private final LinkedList<Statement> statementList = new LinkedList<>();
    private final Interpreter preloadedInterpreter;

    public Parser(String string) {
        this.lexer = new LexicalAnalyzer(string);
        this.string = string;
        this.preloadedInterpreter = new Interpreter(new LinkedList<>());
    }

    public Parser(LexicalAnalyzer lexer) {
        this.lexer = lexer;
        this.string = lexer.getReader().getString();
        this.preloadedInterpreter = new Interpreter(new LinkedList<>());
    }

    public LinkedList<Statement> parse() {
        lexer.getNextToken();
        var parsedStatementList = parseStatementList(new Context());
        preloadedInterpreter.execute();
        return parsedStatementList;
    }

    public LinkedList<Statement> parseStatementList(Context context) {
        return parseStatementList(context, null);
    }

    public LinkedList<Statement> parseStatementList(Context context, TokenType stopLookAhead) {
        LinkedList<Statement> statements = new LinkedList<>();
        do {
            statements.add(parseStatement(context));
        } while (lexer.hasLookAhead() && !lexer.getLookAhead().isType(stopLookAhead));
        return statements;
    }

    public Statement parseStatement(Context context) {
        Token lookAhead = lexer.getLookAhead();
        final Statement statement;
        switch (lookAhead.type()) {
            case LEFT_CURLY_BRACKET -> {
                return this.parseBlockStatement(context);
            }
            case REVERSE -> statement = new ReverseStatement();
            case VARIABLE_DECLARATION -> statement = new VariableDeclarationStatement();
            default -> statement = new ExpressionStatement();
        }
        if (statement.init(this, context)) {
            eat(TokenType.END_STATEMENT);
            if (lexer.hasLookAhead() && lexer.getLookAhead().isType(TokenType.END_STATEMENT)) {
                eat(TokenType.END_STATEMENT);
                preloadedInterpreter.getStatementList().add(statement);
            } else return statement;
        }
        return null;
    }

    public Expression parseExpression(Context context) {
        return parseBinaryExpression(context);
    }

    public Expression parseBinaryExpression(Context context) {
        Expression left = parsePrimaryExpression(context);
        Token lookAhead = lexer.getLookAhead();
        Expression expression;
        switch (lookAhead.type()) {
            case ASSIGNMENT_OPERATOR -> expression = new AssigmentExpression(left);
            case EQUALITY_OPERATOR -> expression = new EqualityExpression(left);
            case RELATIONAL_OPERATOR -> expression = new RelationalExpression(left);
            case ADDITIVE_OPERATOR -> expression = new AdditiveExpression(left);
            case MULTIPLICATIVE_OPERATOR -> expression = new MultiplicativeExpression(left);
            default -> {
                return left;
            }
        }
        expression.init(this, context);
        return expression;
    }

    public Expression parsePrimaryExpression(Context context) {
        Token lookAhead = lexer.getLookAhead();
        Expression expression = switch (lookAhead.type()) {
            case STRING -> new StringLiteralExpression();
            case NUMBER -> new NumberLiteralExpression();
            case BOOLEAN -> new BooleanLiteralExpression();
            case IDENTIFIER -> new IdentifierExpression();
            default -> null;
        };
        if (expression == null) {
            Error.log("Invalid expression.", lookAhead.line(), lookAhead.character());
        } else {
            expression.init(this, context);
        }
        return expression;
    }

    public Statement parseBlockStatement(Context context) {
        BlockStatement block = new BlockStatement();
        block.init(this, context);
        Statement statement = block;
        if (lexer.hasLookAhead() && lexer.getLookAhead().isType(TokenType.END_STATEMENT)) {
            eat(TokenType.END_STATEMENT);
        } else if (lexer.hasLookAhead()) {
            switch (lexer.getLookAhead().type()) {
                case IF -> {
                    statement = new IfStatement(block);
                    statement.init(this, context);
                    eat(TokenType.END_STATEMENT);
                } case FUNCTION -> {
                    statement.init(this, context);
                    eat(TokenType.END_STATEMENT);
                }
            }
        }
        if (lexer.hasLookAhead() && lexer.getLookAhead().isType(TokenType.END_STATEMENT)) {
            eat(TokenType.END_STATEMENT);
            preloadedInterpreter.getStatementList().add(statement);
            return null;
        } else return statement;
    }

    public <T> T eat(TokenType expectedType, Class<T> returnType) {
        return returnType.cast(eat(expectedType).value());
    }

    public Token eat(TokenType expectedType) {
        Token lookAhead = lexer.getLookAhead();
        if (lookAhead == null) {
            Error.log("Unexpected end of input, expected '" + expectedType + "' instead.", lexer.getLine(), lexer.getCharacter());
            System.exit(-1);
        }
        if (!lookAhead.isType(expectedType)) {
            Error.log("Unexpected token type '" + lookAhead.type() + "', expected '" + expectedType + "' instead.", lookAhead.line(), lookAhead.character());
            System.exit(-1);
        }
        lexer.getNextToken();
        return lookAhead;
    }
}
