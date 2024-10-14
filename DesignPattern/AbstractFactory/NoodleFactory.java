package DesignPattern.AbstractFactory;

public class NoodleFactory implements EnterpriseFactory{

    @Override
    public KangProduct kang() {
        return new KangNoodle();
    }

    @Override
    public TongProduct tong() {
        return new TongNoodle();
    }
    
}
