package DesignPattern.Adapter;

public class AliPay {
    public void login(String username){
        System.out.println("Ali logging in " + username);
    }

    public void aliPay(Integer amount){
        System.out.println("Ali pay " + amount);
    }
}
