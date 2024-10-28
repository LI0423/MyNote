package DesignPattern.Facade;

public class MultimediaPlayerFacade {
    private AudioPlayer audioPlayer;
    private VideoPlayer videoPlayer;
    
    public MultimediaPlayerFacade(){
        this.audioPlayer = new AudioPlayer();
        this.videoPlayer = new VideoPlayer();
    }

    public void audioPlay(){
        this.audioPlayer.play();
    }

    public void videoPlay(){
        this.videoPlayer.play();
    }
    
}
