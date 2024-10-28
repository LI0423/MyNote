package DesignPattern.Visitor;

public class Owner implements Person{

    @Override
    public void feed(Cat cat) {
        System.out.println("主人给猫喂食");
    }

    @Override
    public void feed(Dog dog) {
        System.out.println("主人给狗喂食");
    }
    
}
