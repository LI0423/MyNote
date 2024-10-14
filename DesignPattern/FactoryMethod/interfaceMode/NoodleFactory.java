package DesignPattern.FactoryMethod.interfaceMode;

public class NoodleFactory implements Factory {

    @Override
    public Product newProduct() {
        System.out.println("方便面生产工厂 —> 方便面");
        return new NoodelProduct();
    }
    
}
