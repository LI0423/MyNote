package DesignPattern.Singleton;

public class Test {
    public static void main(String[] args) {
        LazySingleton lazySingleton = LazySingleton.getInstance();
        System.out.println(lazySingleton);

        HungrySingleton hungrySingleton = HungrySingleton.getInstance();
        System.out.println(hungrySingleton);

        DoubleSingleton doubleSingleton = DoubleSingleton.getInstance();
        System.out.println(doubleSingleton);
    }
}
