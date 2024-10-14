package DesignPattern.FactoryMethod.abstractMode;

public class Test {
    public static void main(String[] args) {
        NoodleFactory noodleFactory = new NoodleFactory();
        NoodleProduct noodleProduct = (NoodleProduct) noodleFactory.newProduct();
        noodleProduct.show();

        WaterFactory waterFactory = new WaterFactory();
        WaterProduct waterProduct = (WaterProduct) waterFactory.newProduct();;
        waterProduct.show();
    }
}
