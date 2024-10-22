package DesignPattern.SimpleFactory;

public class Test {
    public static void main(String[] args) {
        NoodleFactory noodleFactory = new NoodleFactory();
        KangNoodle kangNoodle = (KangNoodle)noodleFactory.getNoodle(1);
        kangNoodle.show();
        TongNoodle tongNoodle = (TongNoodle)noodleFactory.getNoodle(2);
        tongNoodle.show();
    }

}
