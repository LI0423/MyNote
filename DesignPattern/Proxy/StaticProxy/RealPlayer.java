package DesignPattern.Proxy.StaticProxy;

public class RealPlayer implements Player {

    @Override
    public void loadVideo(String filename) {
        System.out.println("加载MP4视频文件： " + filename);
    }

    @Override
    public void playVideo(String filename) {
        System.out.println("播放MP4视频文件： " + filename);
    }
    
}
