package dev.tenfont.cpmm.lang;

public class Identifier extends Literal<String> {

    public Identifier(String literal) {
        super(literal);
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
