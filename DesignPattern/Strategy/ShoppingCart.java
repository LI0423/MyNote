package DesignPattern.Strategy;

public class ShoppingCart {
    private DiscountStrategy discountStrategy;

    public void setDiscountStrategy(DiscountStrategy discountStrategy){
        this.discountStrategy = discountStrategy;
    }

    public double checkout(double totalAmount){
        if (discountStrategy != null){
            return discountStrategy.applyDiscount(totalAmount);
        }
        return totalAmount;
    }
    
}
