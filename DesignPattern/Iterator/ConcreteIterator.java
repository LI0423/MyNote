package DesignPattern.Iterator;

import java.util.List;

public class ConcreteIterator<T> implements Iterator<T> {
    private List<T> items;
    private int position = 0;

    public ConcreteIterator(List<T> items){
        this.items = items;
    }

    @Override
    public T next() {
        return items.get(position++);
    }

    @Override
    public boolean hasNext() {
        return position < items.size();
    }
    
}
