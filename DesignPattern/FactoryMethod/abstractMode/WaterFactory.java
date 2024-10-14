package DesignPattern.FactoryMethod.abstractMode;

public class WaterFactory extends Factory {

    @Override
    public Product newProduct() {
        System.out.println("矿泉水工厂 -> 矿泉水");
        return new WaterProduct();
    }
    
}
