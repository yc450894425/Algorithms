import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Practice23ConcurrencyII {

    private static class Entry<K, V> {
        private final K key;
        private V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }

        V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }

    public static class SynchronizedHashMap<K, V> {

        private static final int DEFAULT_CAPACITY = 11;
        private static final float DEFAULT_LOAD_FACTOR = 0.75f;//(不加f会默认是double)
        private static final float SCALE_FACTOR = 1.5f;
        private Entry<K, V>[] array;
        private int size;
        private float loadFactor; // used to help determine when to rehash

        public SynchronizedHashMap(int capacity, float loadFactor) {
            array = (Entry<K, V>[]) (new Entry[capacity]);
            size = 0;
            this.loadFactor = loadFactor;
        }

        public SynchronizedHashMap(int capacity) {
            this(capacity, DEFAULT_LOAD_FACTOR);
        }

        public SynchronizedHashMap(float loadFactor) {
            this(DEFAULT_CAPACITY, loadFactor);
        }

        public SynchronizedHashMap() {
            this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
        }

        public synchronized V get(K key) {
            // get index
            int index = getIndex(key);
            // look for key
            Entry<K, V> node = array[index];
            while (node != null) {
                // if find, return value
                if (keysEqual(node.getKey(), key)) {
                    return node.getValue();
                }
                node = node.next;
            }
            return null;
        }

        public synchronized V put(K key, V value) {
            // get index
            int index = getIndex(key);
            // look for key
            Entry<K, V> head = array[index];
            Entry<K, V> node = head;
            while (node != null) {
                if (keysEqual(node.getKey(), key)) {
                    // update
                    V old = node.setValue(value);
                    // return old value
                    return old;
                }
                node = node.next;
            }
            // create new entry before head
            node = new Entry<>(key, value);
            node.next = head;
            array[index] = node;
            // update size and rehash if necessary
            size++;
            if (needRehash()) {
                rehash();
            }
            return null;
        }

        public synchronized V remove(K key) {
            // get index
            int index = getIndex(key);
            // look for key
            Entry<K, V> prev = null;
            Entry<K, V> node = array[index];
            while (node != null) {
                if (keysEqual(node.getKey(), key)) {
                    if (prev == null) {
                        array[index] = node.next;
                    } else {
                        prev.next = node.next;
                    }
                    size--;
                    return node.getValue();
                }
                prev = node;
                node = node.next;
            }
            return null;
        }

        public synchronized boolean containsKey(K key) {
            // get index
            int index = getIndex(key);
            Entry<K, V> node = array[index];
            while (node != null) {
                if (keysEqual(node.getKey(), key)) {
                    return true;
                }
                node = node.next;
            }
            return false;
        }

        public synchronized boolean containsValue(V value) {
            if (isEmpty()) {
                return false;
            }
            for (Entry<K, V> node : array) {
                while (node != null) {
                    if (valuesEqual(node.getValue(), value)) {
                        return true;
                    }
                    node = node.next;
                }
            }
            return false;
        }

        public synchronized int size() {
            return size;
        }

        public synchronized boolean isEmpty() {
            return size == 0;
        }

        public synchronized void clear() {
            Arrays.fill(this.array, null);
            size = 0;
        }

        private int getIndex(K key) {
            return hash(key) % array.length;
        }

        private int hash(K key) {
            if (key == null) {
                return 0;
            }
            // guarantee hash code is not negative;
            return key.hashCode() & 0X7FFFFFFF;
        }

        private boolean needRehash() {
            float ratio = (size + 0.0f) / array.length;
            return ratio >= loadFactor;
        }

        private void rehash() {
            Entry<K, V>[] oldArray = array;
            array = (Entry<K, V>[]) (new Entry[(int) (oldArray.length * SCALE_FACTOR)]);
            for (Entry<K, V> node : oldArray) {
                while (node != null) {
                    Entry<K, V> next = node.next;
                    int index = getIndex(node.getKey());
                    node.next = array[index];
                    array[index] = node;
                    node = next;
                }
            }
        }

        private boolean keysEqual(K key1, K key2) {
            return Objects.equals(key1, key2);
        }

        private boolean valuesEqual(V value1, V value2) {
            return value1 == value2 || value1 != null && value1.equals(value2);
        }
    }

    public static class SynchronizedHashMapByReadWriteLock<K, V> {

        private static final int DEFAULT_CAPACITY = 11;
        private static final float DEFAULT_LOAD_FACTOR = 0.75f;//(不加f会默认是double)
        private static final float SCALE_FACTOR = 1.5f;
        private static final ReadWriteLock RWLOCK = new ReentrantReadWriteLock();
        private Entry<K, V>[] array;
        private int size;
        private float loadFactor; // used to help determine when to rehash

        public SynchronizedHashMapByReadWriteLock(int capacity, float loadFactor) {
            array = (Entry<K, V>[]) (new Entry[capacity]);
            size = 0;
            this.loadFactor = loadFactor;
        }

        public SynchronizedHashMapByReadWriteLock(int capacity) {
            this(capacity, DEFAULT_LOAD_FACTOR);
        }

        public SynchronizedHashMapByReadWriteLock(float loadFactor) {
            this(DEFAULT_CAPACITY, loadFactor);
        }

        public SynchronizedHashMapByReadWriteLock() {
            this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
        }

        public V get(K key) {
            Lock readLock = RWLOCK.readLock();
            readLock.lock();
            try {
                // get index
                int index = getIndex(key);
                // look for key
                Entry<K, V> node = array[index];
                while (node != null) {
                    // if find, return value
                    if (keysEqual(node.getKey(), key)) {
                        return node.getValue();
                    }
                    node = node.next;
                }
                return null;
            } finally {
                readLock.unlock();
            }
        }

        public V put(K key, V value) {
            Lock writeLock = RWLOCK.writeLock();
            writeLock.lock();
            try {
                // get index
                int index = getIndex(key);
                // look for key
                Entry<K, V> head = array[index];
                Entry<K, V> node = head;
                while (node != null) {
                    if (keysEqual(node.getKey(), key)) {
                        // update
                        V old = node.setValue(value);
                        // return old value
                        return old;
                    }
                    node = node.next;
                }
                // create new entry before head
                node = new Entry<>(key, value);
                node.next = head;
                array[index] = node;
                // update size and rehash if necessary
                size++;
                if (needRehash()) {
                    rehash();
                }
                return null;
            } finally {
                writeLock.unlock();
            }
        }

        public V remove(K key) {
            Lock writeLock = RWLOCK.writeLock();
            writeLock.lock();
            try {
                // get index
                int index = getIndex(key);
                // look for key
                Entry<K, V> prev = null;
                Entry<K, V> node = array[index];
                while (node != null) {
                    if (keysEqual(node.getKey(), key)) {
                        if (prev == null) {
                            array[index] = node.next;
                        } else {
                            prev.next = node.next;
                        }
                        size--;
                        return node.getValue();
                    }
                    prev = node;
                    node = node.next;
                }
                return null;
            } finally {
                writeLock.unlock();
            }

        }

        public boolean containsKey(K key) {
            Lock readLock = RWLOCK.readLock();
            readLock.lock();

            try {
                // get index
                int index = getIndex(key);
                Entry<K, V> node = array[index];
                while (node != null) {
                    if (keysEqual(node.getKey(), key)) {
                        return true;
                    }
                    node = node.next;
                }
                return false;
            } finally {
                readLock.unlock();
            }

        }

        public boolean containsValue(V value) {
            Lock readLock = RWLOCK.readLock();
            readLock.lock();

            try {
                if (isEmpty()) {
                    return false;
                }
                for (Entry<K, V> node : array) {
                    while (node != null) {
                        if (valuesEqual(node.getValue(), value)) {
                            return true;
                        }
                        node = node.next;
                    }
                }

                return false;
            } finally {
                readLock.unlock();
            }

        }

        public int size() {
            Lock readLock = RWLOCK.readLock();
            readLock.lock();
            try {
                return size;
            } finally {
                readLock.unlock();
            }
        }

        public boolean isEmpty() {
            Lock readLock = RWLOCK.readLock();
            readLock.lock();
            try {
                return size == 0;
            } finally {
                readLock.unlock();
            }
        }

        public void clear() {
            Lock writeLock = RWLOCK.writeLock();
            writeLock.lock();
            try {
                Arrays.fill(this.array, null);
                size = 0;
            } finally {
                writeLock.unlock();
            }
        }

        private int getIndex(K key) {
            return hash(key) % array.length;
        }

        private int hash(K key) {
            if (key == null) {
                return 0;
            }
            // guarantee hash code is not negative;
            return key.hashCode() & 0X7FFFFFFF;
        }

        private boolean needRehash() {
            float ratio = (size + 0.0f) / array.length;
            return ratio >= loadFactor;
        }

        private void rehash() {
            Entry<K, V>[] oldArray = array;
            array = (Entry<K, V>[]) (new Entry[(int) (oldArray.length * SCALE_FACTOR)]);
            for (Entry<K, V> node : oldArray) {
                while (node != null) {
                    Entry<K, V> next = node.next;
                    int index = getIndex(node.getKey());
                    node.next = array[index];
                    array[index] = node;
                    node = next;
                }
            }
        }

        private boolean keysEqual(K key1, K key2) {
            return Objects.equals(key1, key2);
        }

        private boolean valuesEqual(V value1, V value2) {
            return value1 == value2 || value1 != null && value1.equals(value2);
        }
    }

    public static class volatileTest {

        public static boolean flag = false;

        public static class MyRunnable implements Runnable {
            @Override
            public void run() {
                int i = 0;
                while (!flag) {
                    System.out.println("running");
                    i++;
                }
                System.out.println("finished");
            }
        }

        public static void main(String[] args) throws InterruptedException {
            Thread newThread = new Thread(new MyRunnable());
            newThread.start();
            Thread.sleep(1000);
            flag = true;
            System.out.println("Main thread finished");
        }
    }

    public static class threadSafetyTest {
        public static final HashMap<Integer, String> map = new HashMap<>();

        public static void main(String[] args) {
            Integer key = 65535;
            String value = "value";
            map.put(key, value);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 65535; i++) {
                        map.put(i, "someValue");
                    }
                }
            }).start();
            while (true) {
                if (!value.equals(map.get(key))) {
                    throw new RuntimeException("This Map is not thread safe.");
                }
            }
        }
    }
}
