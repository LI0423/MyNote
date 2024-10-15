package DesignPattern.Bridge;

public class PNGImage extends Image{

    public PNGImage(ImageImpl imageImpl) {
        super(imageImpl);
    }

    @Override
    public void parseFile(String fileName) {
        imageImpl.doPaint("图片为");
        System.out.println(fileName + "格式为PNG。");
    }
    
}
