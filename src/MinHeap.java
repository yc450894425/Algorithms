import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class MinHeap {
    private int[] array;
    private int size;
    private Comparator<Integer> comparator;

    private int compare(Integer i1, Integer i2) {
        if (comparator == null) {
            return i1.compareTo(i2);
        }
        return comparator.compare(i1, i2);
    }

    public MinHeap(int[] array, Comparator<Integer> comparator) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("input array can not be null or empty");
        }
        this.array = array;
        size = array.length;
        this.comparator = comparator;
        heapify();
    }

    public MinHeap(int cap) {
        if (cap <= 0) {
            throw new IllegalArgumentException("capacity can not be <= 0");
        }
        array = new int[cap];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }
    private void percolateUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (compare(array[index], array[parent]) < 0) {
                swap(array, index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void percolateDown(int index) {
        while (index <= size / 2 - 1) {
            int leftChild = index * 2 + 1;
            int rightChild = index * 2 + 2;
            int candidate = leftChild;
            if (rightChild < size && compare(array[rightChild], array[leftChild]) < 0) {
                candidate = rightChild;
            }
            if (compare(array[candidate], array[index]) < 0) {
                swap(array, index, candidate);
                index = candidate;
            } else {
                break;
            }
        }
    }

    public int peek() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        return array[0];
    }

    public int poll() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        int result = array[0];
        array[0] = array[size - 1];
        size--;
        percolateDown(0);
        return result;
    }

    public void offer(int ele) {
        if (isFull()) {
            array = Arrays.copyOf(array, (int)(array.length * 1.5));
        }
        size++;
        array[size - 1] = ele;
        percolateUp(size - 1);
    }

    public int update(int index, int ele) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("invalid index range");
        }
        int prev = array[index];
        array[index] = ele;
        if (ele > prev) {
            percolateDown(index);
        } else if (ele < prev) {
            percolateUp(index);
        }
        return prev;
    }

    private void swap(int[] array, int left, int right) {
        int tmp = array[left];
        array[left] = array[right];
        array[right] = tmp;
    }

    public void heapify() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }
}
