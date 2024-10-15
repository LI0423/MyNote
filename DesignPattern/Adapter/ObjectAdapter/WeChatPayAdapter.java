package DesignPattern.Adapter.ObjectAdapter;

import DesignPattern.Adapter.PayClient;
import DesignPattern.Adapter.WeChatPay;

public class WeChatPayAdapter implements PayClient{

    private WeChatPay weChatPay;

    public WeChatPayAdapter(WeChatPay weChatPay){
        this.weChatPay = weChatPay;
        this.weChatPay.login("wechat");
    }

    @Override
    public void pay(Integer amount) {
        weChatPay.wechatPay(amount);
    }
}
