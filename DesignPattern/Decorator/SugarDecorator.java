package DesignPattern.Decorator;

public class SugarDecorator extends CoffeeDecorator {

    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription(){
        return super.getDescription() + "，加牛奶";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }
    
    
}
