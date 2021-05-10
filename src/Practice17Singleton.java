public class Practice17Singleton {
    private volatile static Practice17Singleton instance;
    private Practice17Singleton() {}
    public static Practice17Singleton getInstance() {
        if (instance == null) {
            synchronized(Practice17Singleton.class) {
                if (instance == null) {
                    instance = new Practice17Singleton();
                }
            }
        }
        return instance;
    }
}

class EagerInitializedSingleton {
    private static EagerInitializedSingleton instance = new EagerInitializedSingleton();
    private EagerInitializedSingleton() {}
    public static EagerInitializedSingleton getInstance() {
        return instance;
    }
}
