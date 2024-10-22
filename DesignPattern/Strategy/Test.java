package DesignPattern.Strategy;

public class Test {
    public static void main(String[] args) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDiscountStrategy(new PercentageDiscountStrategy(0.8));
        double totalAmount = shoppingCart.checkout(100);
        System.out.println(totalAmount);

        shoppingCart.setDiscountStrategy(new FixedAmountDiscountStrategy(30));
        totalAmount = shoppingCart.checkout(100);
        System.out.println(totalAmount);
    }
}
