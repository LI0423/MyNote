package DesignPattern.Model;

public class TestModal {
    public static void main(String[] args) {
        System.out.println("制作红豆豆浆");
        SoyaMilk redSoyaMilk = new RedSoyaMilk();
        redSoyaMilk.make();

        System.out.println("制作花生豆浆");
        SoyaMilk peanutSoyaMilk = new PeanutSoyaMilk();
        peanutSoyaMilk.make();

        System.out.println("制作完成");
    }
}
