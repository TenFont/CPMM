package dev.tenfont.cpmm.lang;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Context {

    // TODO include optional script object, variable map and function registry
    private final @Nullable Context parentContext;

    public Context() {
        this(null);
    }

    public Context enterScope() {
        return new Context(this);
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
