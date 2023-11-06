package dev.tenfont.cpmm.lang.components;

import dev.tenfont.cpmm.lang.FunctionRegistry;
import dev.tenfont.cpmm.lang.VariableMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Context {
    // TODO include optional script object
    private final @Nullable Context parentContext;
    private final VariableMap variableMap;
    private final FunctionRegistry functionRegistry;

    public Context() {
        this(new VariableMap());
    }

    public Context(VariableMap variableMap) {
        this.parentContext = null;
        this.functionRegistry = new FunctionRegistry();
        this.variableMap = variableMap;
    }

    public Context enterScope() {
        return new Context(this, new VariableMap(variableMap), functionRegistry);
    }

    public Context exitScope() {
        if (parentContext == null)
            throw new IllegalStateException("Cannot exit root scope");
        return parentContext;
    }

    public boolean isRootScope() {
        return parentContext == null;
    }

    public void registerFunction(String name, Function<Object[], Void> body) {

    }
}
