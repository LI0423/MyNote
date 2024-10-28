package DesignPattern.Visitor;

public class Someone implements Person{

    @Override
    public void feed(Cat cat) {
        System.out.println("其他人给猫喂食");
    }

    @Override
    public void feed(Dog dog) {
        System.out.println("其他人给狗喂食");
    }
    
}
