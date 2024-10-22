package DesignPattern.SimpleFactory;

public class NoodleFactory {
    private static final int Kang = 1;
    private static final int Tong = 2;
    public Noodle getNoodle(int type){
        switch (type){
            case Kang:
                return new KangNoodle();
            case Tong:
                return new TongNoodle();
            default:
                return null;
        }

    }
}
