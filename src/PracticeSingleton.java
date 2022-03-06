public class PracticeSingleton {
}

class eagerInitializedSingleton {
    private static final eagerInitializedSingleton instance = new eagerInitializedSingleton();

    private eagerInitializedSingleton() {
    }

    public static eagerInitializedSingleton getInstance() {
        return instance;
    }
}

class lazyInitializedSingleton {
    private static volatile lazyInitializedSingleton instance;

    private lazyInitializedSingleton() {

    }

    public static lazyInitializedSingleton getInstance() {
        if (instance == null) {
            // lock the class
            synchronized (lazyInitializedSingleton.class) {
                if (instance == null) {
                    instance = new lazyInitializedSingleton();
                }
            }
        }
        return instance;
    }

}
