package DesignPattern.Visitor;

public class Test {
    public static void main(String[] args) {
        Home home = new Home();
        home.add(new Dog());
        home.add(new Cat());

        Owner owner = new Owner();
        home.action(owner);

        System.out.println("=========");

        Someone someone = new Someone();
        home.action(someone);
    }
}
