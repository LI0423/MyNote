package DesignPattern.Singleton;

/***
 * 双重检测
 * 既支持延迟加载又支持高并发。
 */
public class DoubleSingleton {
    private DoubleSingleton(){}
    private static volatile DoubleSingleton instance;
    public static DoubleSingleton getInstance(){
        if (instance == null){
            synchronized(DoubleSingleton.class){
                if (instance == null) {
                    instance = new DoubleSingleton();
                }
            }
        }
        return instance;
    }
}
