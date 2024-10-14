package DesignPattern.AbstractFactory;

public class WaterFactory implements EnterpriseFactory{

    @Override
    public KangProduct kang() {
        return new KangWater();
    }

    @Override
    public TongProduct tong() {
        return new TongWater();
    }
    
}
