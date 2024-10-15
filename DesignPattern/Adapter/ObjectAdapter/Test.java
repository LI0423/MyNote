package DesignPattern.Adapter.ObjectAdapter;

import DesignPattern.Adapter.AliPay;
import DesignPattern.Adapter.PayClient;
import DesignPattern.Adapter.WeChatPay;

public class Test {
    public static void main(String[] args) {
        WeChatPay weChatPay = new WeChatPay();
        PayClient weChatPayClient = new WeChatPayAdapter(weChatPay);
        weChatPayClient.pay(5);

        AliPay aliPay = new AliPay();
        PayClient aliPayAdapter = new AliPayAdapter(aliPay);
        aliPayAdapter.pay(10);
    }
}
