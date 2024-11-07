package DesignPattern.Flyweight;

public class PoisonPotion implements Potion{

    @Override
    public void drink() {
        System.out.println("This is poisonous. (Potion= " + System.identityHashCode(this) + " )");
    }
    
}
