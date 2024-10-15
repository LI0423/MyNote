package DesignPattern.Bridge;

public class JPGImage extends Image{

    public JPGImage(ImageImpl imageImpl) {
        super(imageImpl);
    }

    @Override
    public void parseFile(String fileName) {
        imageImpl.doPaint("图片为");
        System.out.println(fileName + "，格式为JPG。");
    }
    
}
