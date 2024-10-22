package DesignPattern.Proxy.StaticProxy;

public class PlayerProxy implements Player {

    private RealPlayer realPlayer;

    public PlayerProxy(RealPlayer realPlayer){
        this.realPlayer = realPlayer;
    }

    @Override
    public void loadVideo(String filename) {
        this.realPlayer.loadVideo(filename);
    }

    @Override
    public void playVideo(String filename) {
        this.realPlayer.playVideo(filename);
    }
    
}
