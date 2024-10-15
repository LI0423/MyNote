package DesignPattern.Adapter.InterfaceAdapter;

public abstract class Adapter implements ElectricPay{

    @Override
    public String login(String username) {
        return null;
    }

    @Override
    public String pay(Integer amount) {
        return null;
    }
    
}
