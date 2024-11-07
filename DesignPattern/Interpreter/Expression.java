package DesignPattern.Interpreter;

public abstract class Expression {
    public abstract int interpret();

    @Override
    public abstract String toString();
}
