package DesignPattern.Bridge;

public class WindowsImpl implements ImageImpl {

    @Override
    public void doPaint(String text) {
        System.out.println("在Windows操作系统中显示图像：" + text);
    }
    
}
