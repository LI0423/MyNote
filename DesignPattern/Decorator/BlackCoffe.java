package DesignPattern.Decorator;

public class BlackCoffe implements Coffee {

    @Override
    public String getDescription() {
        return "黑咖啡";
    }

    @Override
    public double getCost() {
        return 2.0;
    }
    
}
