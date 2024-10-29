package DesignPattern.Iterator;

import java.util.ArrayList;
import java.util.List;

public class ConcreteBookShelf implements BookShelf {

    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
    }

    @Override
    public Iterator<Book> createIterator() {
        return new ConcreteIterator<>(books);
    }
    
}
