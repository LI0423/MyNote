package DesignPattern.AbstractFactory;

public class Test {
    public static void main(String[] args) {

        NoodleFactory noodleFactory = new NoodleFactory();
        KangNoodle kangNoodle = (KangNoodle) noodleFactory.kang();
        System.out.println(kangNoodle.getCompanyName() + " " + kangNoodle.getName());
        TongNoodle tongNoodle = (TongNoodle) noodleFactory.tong();
        System.out.println(tongNoodle.getCompanyName() + " " + tongNoodle.getName());

        WaterFactory waterFactory = new WaterFactory();
        KangWater kangWater = (KangWater) waterFactory.kang();
        System.out.println(kangWater.getCompanyName() + " " + kangWater.getName());
        TongWater tongWater = (TongWater) waterFactory.tong();
        System.out.println(tongWater.getCompanyName() + " " + tongWater.getName());
        
    }
}
