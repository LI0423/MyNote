package DesignPattern.Singleton;

/***
 * 饿汉式单例
 * 类一旦加载就创建一个单例，在调用getInstance之前单例就存在。
 */
public class HungrySingleton {

    private static volatile HungrySingleton instance = new HungrySingleton();

    private HungrySingleton(){}

    public static HungrySingleton getInstance(){
        return instance;
    }

}
