package DesignPattern.Bridge;

public class Test {
    public static void main(String[] args) {
        ImageImpl macImpl = new MacImpl();
        Image jpgImage = new JPGImage(macImpl);
        jpgImage.parseFile("jpg图片");

        ImageImpl windowsImpl = new WindowsImpl();
        Image pngImage = new PNGImage(windowsImpl);
        pngImage.parseFile("png图片");
    }
}
