package DesignPattern.Adapter.InterfaceAdapter;

public class Test {
    public static void main(String[] args) {
        WeChatAdapter weChatAdapter = new WeChatAdapter();
        System.out.println(weChatAdapter.login("wechat"));
        System.out.println(weChatAdapter.pay(5));
    }
}
