package DesignPattern.Adapter.ClassAdapter;

import DesignPattern.Adapter.PayClient;
import DesignPattern.Adapter.WeChatPay;

public class WeChatPayAdapter extends WeChatPay implements PayClient{

    @Override
    public void pay(Integer amount) {
        wechatPay(amount);
    }
    
}
