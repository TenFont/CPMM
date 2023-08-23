package dev.tenfont.cpmm.lang.components;

import dev.tenfont.cpmm.lang.variables.VariableMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Context {

    // TODO include optional script object, variable map and function registry
    private final @Nullable Context parentContext;
    private final VariableMap variableMap;

    public Context() {
        this(new VariableMap());
    }

    public Context(VariableMap variableMap) {
        this.parentContext = null;
        this.variableMap = variableMap;
    }

    public VariableMap getVariableMap() {
        return variableMap;
    }

    public Context enterScope() {
        return new Context(this, variableMap);
    }

    public Context exitScope() {
        if (parentContext == null)
            throw new IllegalStateException("Cannot exit root scope");
        return parentContext;
    }

    public boolean isRootScope() {
        return parentContext == null;
    }

}
