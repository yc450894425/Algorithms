import java.util.Comparator;

public class Practice10ImplementingHeaps {

    private static final double DEFAULT_EXPAND_COEFFICIENT = 1.5;
    private static final int DEFAULT_CAPACITY = 11;

    public static class MyHeap<E extends Comparable<E>> {

        private E[] array;
        private int size;
        private double expandCoefficient;
        private Comparator<E> comparator;

        public MyHeap(int capacity, Comparator<E> comparator) {
            array = (E[]) new Comparable[capacity];
            size = 0;
            expandCoefficient = DEFAULT_EXPAND_COEFFICIENT;
            this.comparator = comparator;
        }

        public MyHeap(Comparator<E> comparator) {
            this(DEFAULT_CAPACITY, comparator);
        }

        public MyHeap(int capacity) {
            this(capacity, null);
        }

        public MyHeap() {
            this(DEFAULT_CAPACITY, null);
        }

        public MyHeap(E[] array, Comparator<E> comparator) {
            if (array == null || array.length == 0) {
                throw new IllegalArgumentException("Input array cannot be null or empty.");
            }
            this.array = array;
            this.size = array.length;
            this.comparator = comparator;
            heapify();
        }

        public MyHeap(E[] array) {
            this(array, null);
        }

        private void heapify() {
            for (int i = size / 2 - 1; i >= 0; i--) {
                percolateDown(i);
            }
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private boolean isFull() {
            return size == array.length;
        }

        public void offer(E element) {
            // Check if isFull.
            if (isFull()) {
                expand();
            }
            array[size] = element;
            size++;
            percolateUp(size - 1);
        }

        public E peek() {
            if (isEmpty()) {
                return null;
            }
            return array[0];
        }

        public E poll() {
            if (isEmpty()) {
                return null;
            }
            E result = array[0];
            array[0] = array[size - 1];
            size--;
            percolateDown(0);
            return result;
        }

        public E update(int index, E element) {
            if (index >= size || index < 0) {
                return null;
            }
            E old = array[index];
            array[index] = element;
            if (compare(element, old) < 0) {
                percolateUp(index);
            } else if (compare(element, old) > 0) {
                percolateDown(index);
            }
            return old;
        }

        private void percolateUp(int index) {
            if (index >= size) {
                return;
            }
            int parent = (index - 1) / 2;
            while (parent >= 0 && compare(array[index], array[parent]) < 0) {
                swap(array, index, parent);
                index = parent;
                parent = (index - 1) / 2;
            }
        }

        private void percolateDown(int index) {
            while (index <= size / 2 - 1) {
                int left = 2 * index + 1;
                int right = 2 * index + 2;
                int candidate = left;
                if (right < size) {
                    candidate = compare(array[left], array[right]) < 0 ? left : right;
                }

                if (compare(array[candidate], array[index]) < 0) {
                    swap(array, candidate, index);
                    index = candidate;
                } else {
                    break;
                }
            }
        }

        private void expand() {
            E[] old = array;
            array = (E[]) new Comparable[(int) (size * expandCoefficient)];
            for (int i = 0; i < old.length; i++) {
                array[i] = old[i];
            }
        }

        private void swap(E[] array, int i, int j) {
            E tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }

        private int compare(E o1, E o2) {
            if (comparator == null) {
                return o1.compareTo(o2);
            } else {
                return comparator.compare(o1, o2);
            }
        }
    }
}
