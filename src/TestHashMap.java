public class TestHashMap {

    private static class Entry {
        final String key;
        Integer value;
        Entry next;
        public Entry(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    Entry[] array;


    public TestHashMap() {
        array = new Entry[11];
    }

    public TestHashMap(int size) {
        array = new Entry[size];
    }

    private int hash(String key) {
        if (key == null) {
            return 0;
        }
        int hashNumber = key.hashCode();
        return hashNumber;
    }

    private int getIndex(int hashNumber) {
        return hashNumber % array.length;
    }

    public Integer put(String key, Integer value) {
        int hashNumber = hash(key);
        int index = getIndex(hashNumber);
        Entry head = array[index];
        Entry curr = head;
        while (curr != null) {
            if (key == curr.key) {
                Integer old = curr.value;
                curr.value = value;
                return old;
            }
            curr = curr.next;
        }
        curr = new Entry(key, value);
        if (head == null) {
            array[index] = curr;
        } else {
            curr.next = head.next;
            head.next = curr;
        }
        return null;
    }
    public Integer get(String key) {
        int hashNumber = hash(key);
        int index = getIndex(hashNumber);
        Entry curr = array[index];
        while (curr != null) {
            if (key == curr.key) {
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }
}
