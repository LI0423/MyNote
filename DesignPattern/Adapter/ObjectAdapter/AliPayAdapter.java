package DesignPattern.Adapter.ObjectAdapter;

import DesignPattern.Adapter.AliPay;
import DesignPattern.Adapter.PayClient;

public class AliPayAdapter implements PayClient{

    private AliPay aliPay;

    public AliPayAdapter(AliPay aliPay){
        this.aliPay = aliPay;
        this.aliPay.login("ali");
    }

    @Override
    public void pay(Integer amount) {
        this.aliPay.aliPay(amount);
    }
    
}
