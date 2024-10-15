package DesignPattern.Adapter.ClassAdapter;

import DesignPattern.Adapter.PayClient;

public class Test {
    public static void main(String[] args) {
        PayClient payClient = new WeChatPayAdapter();
        payClient.pay(5);

        PayClient aliPayClient = new AliPayAdapter();
        aliPayClient.pay(10);
    }
}
