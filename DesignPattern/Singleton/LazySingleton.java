package DesignPattern.Singleton;

/***
 * 懒汉式单例
 * 类加载时没有生成单例，只有当第一次调用getInstance方法时才去创建这个单例。
 */
public class LazySingleton {
    private static volatile LazySingleton instance = null;

    private LazySingleton(){}

    public static synchronized LazySingleton getInstance(){
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
