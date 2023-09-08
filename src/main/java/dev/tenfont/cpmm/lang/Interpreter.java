package dev.tenfont.cpmm.lang;

import dev.tenfont.cpmm.elements.statements.ReverseStatement;
import dev.tenfont.cpmm.lang.components.Context;
import dev.tenfont.cpmm.lang.components.Statement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

@RequiredArgsConstructor
@Getter
@Setter
public class Interpreter {
    private final LinkedList<Statement> statementList;
    private int index = 0;
    private Context currentContext = new Context();

    public void execute() {
        boolean reversed = false;
        while (index < statementList.size() && index >= 0) {
            Statement statement = statementList.get(reversed ? index-- : index++);
            if (statement == null) continue;
            if (statement instanceof ReverseStatement) {
                reversed = !reversed;
                if (reversed) index-=2;
                else index+=2;
            } else {
                statement.execute(this);
            }
        }
    }
}
