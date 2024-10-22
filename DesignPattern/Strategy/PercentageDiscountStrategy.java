package DesignPattern.Strategy;

public class PercentageDiscountStrategy implements DiscountStrategy {

    private double percentage;

    public PercentageDiscountStrategy(double percentage){
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double amount) {
        return amount - (amount * percentage / 100);
    }
    
}
