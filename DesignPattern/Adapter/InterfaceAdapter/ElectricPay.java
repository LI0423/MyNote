package DesignPattern.Adapter.InterfaceAdapter;

public interface ElectricPay {
    public String login(String username);
    public String pay(Integer amount);
}
