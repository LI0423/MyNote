package DesignPattern.Adapter.ClassAdapter;

import DesignPattern.Adapter.AliPay;
import DesignPattern.Adapter.PayClient;
import DesignPattern.Adapter.WeChatPay;

public class Test {
    public static void main(String[] args) {
        WeChatPay weChatPay = new WeChatPay();
        PayClient payClient = new WeChatPayAdapter(weChatPay);
        payClient.pay(5);

        AliPay aliPay = new AliPay();
        PayClient aliPayClient = new AliPayAdapter(aliPay);
        aliPayClient.pay(10);
    }
}
