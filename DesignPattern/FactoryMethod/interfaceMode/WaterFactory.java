package DesignPattern.FactoryMethod.interfaceMode;

public class WaterFactory implements Factory{

    @Override
    public Product newProduct() {
        System.out.println("矿泉水生产工厂 -> 矿泉水");
        return new WaterProduct();
    }
    
}
