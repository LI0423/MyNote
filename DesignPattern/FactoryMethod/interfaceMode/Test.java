package DesignPattern.FactoryMethod.interfaceMode;

public class Test {
    public static void main(String[] args) {
        NoodleFactory noodleFactory = new NoodleFactory();
        NoodelProduct noodelProduct = (NoodelProduct)noodleFactory.newProduct();
        noodelProduct.show();

        WaterFactory waterFactory = new WaterFactory();
        WaterProduct waterProduct = (WaterProduct)waterFactory.newProduct();
        waterProduct.show();

        RiceFactory riceFactory = new RiceFactory();
        RiceProduct riceProduct = (RiceProduct) riceFactory.newProduct();
        riceProduct.show();
    }
}
