package DesignPattern.Visitor;

public class Cat implements Animal{

    @Override
    public void accept(Person person) {
        person.feed(this);
        System.out.println("宠物猫接受喂食");
    }
    
}
