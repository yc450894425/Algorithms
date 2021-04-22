import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Practice14HashMapImplementation {

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

    public static class CustomHashMap<K, V> {

        private static final int DEFAULT_CAPACITY = 11;
        private static final float DEFAULT_LOAD_FACTOR = 0.75f;//(不加f会默认是double)
        private static final float SCALE_FACTOR = 1.5f;
        private Entry<K, V>[] array;
        private int size;
        private float loadFactor; // used to help determine when to rehash

        public CustomHashMap(int capacity, float loadFactor) {
            array = (Entry<K, V>[]) (new Entry[capacity]);
            size = 0;
            this.loadFactor = loadFactor;
        }

        public CustomHashMap(int capacity) {
            this(capacity, DEFAULT_LOAD_FACTOR);
        }

        public CustomHashMap(float loadFactor) {
            this(DEFAULT_CAPACITY, loadFactor);
        }

        public CustomHashMap() {
            this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
        }

        public V get(K key) {
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

        public V put(K key, V value) {
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

        public V remove(K key) {
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

        public boolean containsKey(K key) {
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

        public boolean containsValue(V value) {
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

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void clear() {
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
            return  ratio >= loadFactor;
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

    private static void isMapOrdered() {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        for (int i = 10; i >= 0; i--) {
            map.put(i, i);
        }
        map.forEach((k, v) -> {
            System.out.print(k + " ");
        });
    }
}
