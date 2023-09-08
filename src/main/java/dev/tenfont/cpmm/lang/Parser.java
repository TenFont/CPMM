package dev.tenfont.cpmm.lang;

import dev.tenfont.cpmm.elements.expressions.binaryexpressions.AdditiveExpression;
import dev.tenfont.cpmm.elements.expressions.binaryexpressions.AssigmentExpression;
import dev.tenfont.cpmm.elements.expressions.literals.BooleanLiteralExpression;
import dev.tenfont.cpmm.elements.expressions.literals.IdentifierExpression;
import dev.tenfont.cpmm.elements.expressions.literals.NumberLiteralExpression;
import dev.tenfont.cpmm.elements.expressions.literals.StringLiteralExpression;
import dev.tenfont.cpmm.elements.statements.*;
import dev.tenfont.cpmm.lang.components.*;
import dev.tenfont.cpmm.util.Error;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Parser {
    private final LexicalAnalyzer lexer;
    private final String string;
    private final LinkedList<Statement> statementList = new LinkedList<>();

    public Parser(String string) {
        this.lexer = new LexicalAnalyzer(string);
        this.string = string;
    }

    public Parser(LexicalAnalyzer lexer) {
        this.lexer = lexer;
        this.string = lexer.getReader().getString();
    }

    public LinkedList<Statement> parse() {
        lexer.getNextToken();
        return parseStatementList(new Context());
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
        final Statement statement = switch (lookAhead.type()) {
            case LEFT_CURLY_BRACKET -> new BlockStatement();
            case REVERSE -> new ReverseStatement();
            case FUNCTION -> new FunctionDeclarationStatement();
            case VARIABLE_DECLARATION -> new VariableDeclarationStatement();
            default -> new ExpressionStatement();
        };
        if (statement.init(this, context)) {
            if (lexer.hasLookAhead() && lexer.getLookAhead().isType(TokenType.END_STATEMENT)) {
                eat(TokenType.END_STATEMENT);
                new Interpreter(new LinkedList<>(List.of(statement))).execute();
            } else return statement;
        }
        return null;
    }

    public Expression<?> parseExpression(Context context, Class<?> expectedType) {
        return parseBinaryExpression(context, expectedType);
    }

    public Expression<?> parseBinaryExpression(Context context, Class<?> expectedType) {
        Expression<?> left = parsePrimaryExpression(context, expectedType);
        Token lookAhead = lexer.getLookAhead();
        Expression<?> expression;
        switch (lookAhead.type()) {
            case ASSIGNMENT_OPERATOR -> expression = new AssigmentExpression(left);
            case ADDITIVE_OPERATOR -> expression = new AdditiveExpression(left);
            default -> {
                return left;
            }
        }
        if (!expectedType.isAssignableFrom(expression.getReturnType())) {
            Error.log("Invalid expression type '" + expression.getReturnType().getSimpleName() + "', expected '" + expectedType.getSimpleName() + "' instead.", lookAhead.line(), lookAhead.character());
        } else if (expression.init(this, context)) {
            return expression;
        }
        return expression;
    }

    public Expression<?> parsePrimaryExpression(Context context, Class<?> expectedType) {
        Token lookAhead = lexer.getLookAhead();
        Expression<?> expression = switch (lookAhead.type()) {
            case STRING -> new StringLiteralExpression();
            case NUMBER -> new NumberLiteralExpression();
            case BOOLEAN -> new BooleanLiteralExpression();
            case IDENTIFIER -> new IdentifierExpression();
            default -> null;
        };
        if (expression == null) {
            Error.log("Invalid expression.", lookAhead.line(), lookAhead.character());
        } else if (!expectedType.isAssignableFrom(expression.getReturnType())) {
            Error.log("Invalid expression type '" + expression.getReturnType().getSimpleName() + "', expected '" + expectedType.getSimpleName() + "' instead.", lookAhead.line(), lookAhead.character());
        } else if (expression.init(this, context)) {
            return expression;
        }
        return expression;
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
