package DesignPattern.Proxy.StaticProxy;

public class Test {
    public static void main(String[] args) {
        RealPlayer realPlayer = new RealPlayer();
        realPlayer.loadVideo("西游记.mp4");
        realPlayer.playVideo("西游记.mp4");

        PlayerProxy playerProxy = new PlayerProxy(realPlayer);
        playerProxy.loadVideo("三国演义.mp4");
        playerProxy.playVideo("三国演义.mp4");

    }
}
