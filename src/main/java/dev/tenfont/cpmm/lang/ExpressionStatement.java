package dev.tenfont.cpmm.lang;

public abstract class ExpressionStatement<T> implements Expression<T> {

    public void execute(Context context) {
        get(context);
    }

    public Statement asStatement() {
        return new AsStatement();
    }

    private class AsStatement implements Statement {

        @Override
        public boolean init(Context context) {
            return ExpressionStatement.this.init(context);
        }

        @Override
        public void execute(Context context) {
            ExpressionStatement.this.execute(context);
        }

    }

}
