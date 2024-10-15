package DesignPattern.Bridge;

public abstract class Image {
    protected ImageImpl imageImpl;

    public Image(ImageImpl imageImpl){
        this.imageImpl = imageImpl;
    }

    public abstract void parseFile(String fileName);

}
