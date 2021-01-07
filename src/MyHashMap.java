// HashMap Implementation
// C: Key: String, Value: Integer, Hash Collision: Separate Chaining,
//	1. Define APIs:
//		a. Integer Get(String key),
//			if get, return key; if cannot get, return null;
//		b. Integer Put(String key, Integer value),
//			if new, return null; if existed, return old value;
//	2. Clarify data field
//		a. capacity, loadFactor
//		b. size
//		c. Entry[]
//	3. Define Constructor:
//		a. Default Constructor
//		b. Constructor(int capacity, float loadFactor)

public class MyHashMap<K, V> {
    public static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getkey() {
            return key;
        }
        public V getValue() {
            return value;
        }
        public void setValue(V value) {
            this.value = value;
        }
    }

    private static final int DEFAULT_CAPACITY = 11;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;//(不加f会默认是double)
    private Entry<K, V>[] array;
    private int size;
    private float loadFactor;
    private static final float SCALE_FACTOR = 1.5f;

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int capacity, float loadFactor) {
        this.size = 0;
        this.loadFactor = loadFactor;
        array = (Entry<K, V>[]) new Entry[capacity];
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }



    public V get(K key) {
        if (key == null) {
            return null;
        }
        int index = getIndex(key);
        Entry<K, V> entry = array[index];
        while (entry != null) {
            if (equals(entry.getkey(), key)) {
                return entry.getValue();
            }
            entry = entry.next;
        }
        return null;
    }

    public V put (K key, V value) {
        if (size >= (int)(loadFactor * array.length)) {
            reSize();
        }
        int index = getIndex(key);
        Entry<K, V> head = array[index];
        Entry<K, V> cur = head;
        while (cur != null) {
            if (equals(cur.getkey(), key)) {
                V result = cur.getValue();
                cur.setValue(value);
                return result;
            }
            cur = cur.next;
        }
        Entry<K, V> newHead = new Entry<>(key, value);
        newHead.next = head;
        array[index] = newHead;
        size++;
        return null;
    }

    private int getIndex(K key) {
        int hashCode = key == null ? 0 : key.hashCode();
        if (hashCode < 0) {
            hashCode *= -1;
        }
        return hashCode % array.length;
    }

    private void reSize() {
        Entry<K, V>[] oldArray = array;
        array = (Entry<K, V>[])new Entry[(int)(array.length * SCALE_FACTOR)];
        for (Entry<K, V> entry : oldArray) {
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int index = getIndex(entry.getkey());
                entry.next = array[index];
                array[index] = entry;
                entry = next;
            }
        }
    }

    public V remove(K key) {
        int index = getIndex(key);
        Entry<K, V> curr = array[index];
        Entry<K, V> prev = null;
        while (curr != null) {
            if (equals(curr.getkey(), key)) {
                if (prev == null) {
                    array[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return curr.getValue();
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    public boolean containsKey(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = array[index];
        while (entry != null) {
            if (equals(entry.getkey(), key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    public boolean containsValue(V value) {
        for (Entry<K, V> entry : array) {
            while (entry != null) {
                if (equals(entry.getValue(), value)) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    private boolean equals(Object one, Object two) {
        return one == null && two == null || one != null && one.equals(two);
    }
}
