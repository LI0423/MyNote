package DesignPattern.Strategy;

public class FixedAmountDiscountStrategy implements DiscountStrategy {

    private double discountAmount;

    public FixedAmountDiscountStrategy(double discountAmount){
        this.discountAmount = discountAmount;
    }

    @Override
    public double applyDiscount(double amount) {
        return amount - discountAmount;
    }
    
}
