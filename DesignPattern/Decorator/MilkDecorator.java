package DesignPattern.Decorator;

public class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription(){
        return super.getDescription() + ", 加牛奶";
    }
    
    @Override
    public double getCost(){
        return super.getCost() + 0.3;
    }
}
