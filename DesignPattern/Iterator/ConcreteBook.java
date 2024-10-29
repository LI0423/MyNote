package DesignPattern.Iterator;

public class ConcreteBook implements Book{
    private String title;
    private String author;

    public ConcreteBook(String title, String author){
        this.title = title;
        this.author = author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" + 
            "title='" + title + '\'' +
            ", author='" + author + '\'' +
            '}';
    }
    
}
