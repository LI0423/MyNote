package DesignPattern.FactoryMethod.abstractMode;

public class NoodleFactory extends Factory {

    @Override
    public Product newProduct() {
        System.out.println("方便面工厂 -> 方便面");
        return new NoodleProduct();
    }
    
}
