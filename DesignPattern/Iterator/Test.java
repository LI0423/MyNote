package DesignPattern.Iterator;

public class Test {
    public static void main(String[] args) {
        ConcreteBookShelf bookShelf = new ConcreteBookShelf();
        bookShelf.addBook(new ConcreteBook("Java Design Patterns", "Sven Walter"));
        bookShelf.addBook(new ConcreteBook("Clean Code", "Robert C. Martin"));

        Iterator<Book> iterator = bookShelf.createIterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println(book);
        }
    }
}
