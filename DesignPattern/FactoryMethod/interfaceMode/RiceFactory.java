package DesignPattern.FactoryMethod.interfaceMode;

public class RiceFactory implements Factory {

    @Override
    public Product newProduct() {
        System.out.println("大米生产工厂 -> 大米");
        return new RiceProduct();
    }
    
}
