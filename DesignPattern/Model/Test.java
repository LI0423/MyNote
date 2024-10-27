package DesignPattern.Model;

public class Test {
    public static void main(String[] args) {
        System.out.println("制作红豆豆浆");
        SoyaMilk redSoyaMilk = new RedSoyaMilk();
        redSoyaMilk.make();

        System.out.println("制作花生豆浆");
        SoyaMilk peanutSoyaMilk = new PeanutSoyaMilk();
        peanutSoyaMilk.make();

        System.out.println("制作完成");

        SoyaMilk redSoyaMilk2 = new SoyaMilk() {
            @Override
            public void addCondiments(){
                System.out.println("加入上好的花生");
            }
        };
        redSoyaMilk2.make();
    }
}
