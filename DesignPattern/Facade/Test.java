package DesignPattern.Facade;

public class Test {
    public static void main(String[] args) {
        MultimediaPlayerFacade facade = new MultimediaPlayerFacade();
        facade.audioPlay();
        facade.videoPlay();
    }
}
