package DesignPattern.Proxy.JDKProxy;

import DesignPattern.Proxy.StaticProxy.Player;
import DesignPattern.Proxy.StaticProxy.RealPlayer;

public class Test {
    public static void main(String[] args) {
        RealPlayer realPlayer = new RealPlayer();
        Player proxy = new JDKProxyFactory(realPlayer).getProxy();
        proxy.loadVideo("红楼梦.mp4");
        proxy.playVideo("红楼梦.mp4");
    }
}
