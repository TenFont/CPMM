package dev.tenfont.cpmm.lang.components;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public abstract class BinaryExpression extends Expression {
    private final Expression left;
}
