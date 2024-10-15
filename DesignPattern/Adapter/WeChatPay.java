package DesignPattern.Adapter;

public class WeChatPay {

    public void login(String username){
        System.out.println("WeChat logging in " + username);
    }

    public void wechatPay(Integer amount){
        System.out.println("WeChat pay: " + amount);
    }

}