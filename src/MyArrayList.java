import java.lang.reflect.Array;

public class MyArrayList<E> {
    private static final int DEFAULT_CAPACITY = 11;
    private static final double DEFAULT_EXPAND_COEFFICIENT = 1.5;
    private E[] array;
    private int size;
    private double expandCoefficient;

    public MyArrayList(int capacity, double expandCoefficient) {
        // Suggested implementation in Effective Java but dangerous. Might cause unexpected errors or ClassCastException.
        array = (E[]) new Object[capacity];
        this.expandCoefficient = expandCoefficient;
        size = 0;
    }

    public MyArrayList(int capacity) {
         this(capacity, DEFAULT_EXPAND_COEFFICIENT);
    }

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public E get(int index) {
        if (index >= size) {
            return null;
        }
        return array[index];
    }

    public E set(int index, E e) {
        if (index >= size) {
            return null;
        }
        E old = array[index];
        array[index] = e;
        return old;
    }

    public int size() {
        return size;
    }

    public void add(E e) {
        if (size == array.length) {
            expand();
        }
        array[size++] = e;
    }

    public void add(int index, E e) {
        if (size == array.length) {
            expand();
        }
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = e;
        size++;
    }

    public E remove() {
        if (size == 0) {
            return null;
        }
        E old = array[--size];
        array[size] = null;
        return old;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        E old = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--size] = null;
        return old;
    }

    private void expand() {
        E[] oldArray = array;
        array = (E[]) new Object[(int) (array.length * expandCoefficient)];
        copy(oldArray, array);
    }

    private void copy(E[] oldArray, E[] newArray) {
        for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }
    }

}
