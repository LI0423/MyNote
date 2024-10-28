package DesignPattern.Visitor;

public class Dog implements Animal{

    @Override
    public void accept(Person person) {
        person.feed(this);
        System.out.println("宠物狗接受喂食");
    }
    
}
