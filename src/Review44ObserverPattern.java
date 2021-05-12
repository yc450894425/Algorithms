public class Review44ObserverPattern {
    public static void main(String[] args) {
        MyObserver observer = new MyObserver();
        MyObservable observable = new MyObservable(0, observer);
        for (int i = 0; i < 10; i++) {
            observable.addValue(i);
        }
    }
}

class MyObservable {
    int value;
    MyObserver observer;

    public MyObservable(int init, MyObserver observer) {
        value = init;
        this.observer = observer;
    }

    public void addValue(int addend) {
        value += addend;
        observer.onChange(value);
    }
}

class MyObserver {

    public void onChange(int value) {
        System.out.println("I observed that the value has been changed to " + value);
    }

}
