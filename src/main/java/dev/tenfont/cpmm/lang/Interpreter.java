package dev.tenfont.cpmm.lang;

import dev.tenfont.cpmm.elements.statements.EnterScopeStatement;
import dev.tenfont.cpmm.elements.statements.ExitScopeStatement;
import dev.tenfont.cpmm.elements.statements.ReverseStatement;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Statement;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;

@RequiredArgsConstructor
public class Interpreter {
    private final LinkedList<Statement> statementList;

    public void execute() {
        Context context = new Context();
        int index = 0;
        boolean reversed = false;
        while (index < statementList.size() && index >= 0) {
            Statement statement = statementList.get(reversed ? index-- : index++);
            if (statement instanceof ReverseStatement) {
                reversed = !reversed;
                if (reversed) index-=2;
                else index+=2;
            } else if (statement instanceof EnterScopeStatement) {
                context = context.enterScope();
            } else if (statement instanceof ExitScopeStatement) {
                context = context.exitScope();
            }
            else statement.execute(context);
        }
    }
}
