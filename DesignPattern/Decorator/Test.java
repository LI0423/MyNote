package DesignPattern.Decorator;

public class Test {
    public static void main(String[] args) {
        Coffee blackCoffe = new BlackCoffe();
        blackCoffe = new MilkDecorator(blackCoffe);
        blackCoffe = new SugarDecorator(blackCoffe);
        System.out.println(blackCoffe.getDescription());
        System.out.println(blackCoffe.getCost());

        Coffee bCoffee = new SugarDecorator(new MilkDecorator(new BlackCoffe()));
        System.out.println(bCoffee.getDescription());
        System.out.println(bCoffee.getCost());
    }
}
