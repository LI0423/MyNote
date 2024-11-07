package DesignPattern.Prototype;

public abstract class Prototype<T> implements Cloneable {

    @SuppressWarnings("unchecked")
    public T copy() throws CloneNotSupportedException{
        return (T) super.clone();
    }
}
