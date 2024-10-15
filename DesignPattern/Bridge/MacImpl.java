package DesignPattern.Bridge;

public class MacImpl implements ImageImpl{

    @Override
    public void doPaint(String text) {
        System.out.println("在Mac操作系统中显示图像：" + text);
    }
    
}
