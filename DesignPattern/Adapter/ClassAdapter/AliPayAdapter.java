package DesignPattern.Adapter.ClassAdapter;

import DesignPattern.Adapter.AliPay;
import DesignPattern.Adapter.PayClient;

public class AliPayAdapter extends AliPay implements PayClient{

    @Override
    public void pay(Integer amount) {
        aliPay(amount);
    }

}
