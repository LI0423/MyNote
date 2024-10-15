package DesignPattern.Adapter.InterfaceAdapter;

public class WeChatAdapter extends Adapter{

    @Override
    public String login(String username) {
        return "WeChat logging in " + username;
    }

    @Override
    public String pay(Integer amount) {
        return "WeChat pay " + amount;
    }
    
}
