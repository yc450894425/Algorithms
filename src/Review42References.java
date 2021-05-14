import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

public class Review42References {

    static class SoftReferenceTest {
        // soft reference queue
        private static final ReferenceQueue<String> QUEUE = new ReferenceQueue<>();

        public static void test() {
            // strong reference
            String strongRef = new String("abc");
            // soft reference
            String s = new String("abc");
            SoftReference<String> soft = new SoftReference<>(s, QUEUE);

            s = null;
            System.gc();

            System.out.println(soft.get());
            Reference<? extends String> curr = QUEUE.poll();
            System.out.println(curr);
        }

    }

    static class GCTarget {
        private int id;
        private byte[] content = new byte[1024];

        public GCTarget(int id) {
            this.id = id;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Finalizing GCTarget, id: " + id);
        }
    }

    static class MyWeakReference extends WeakReference<GCTarget> {
        public int id;
        private GCTarget target;

        public MyWeakReference(GCTarget target, ReferenceQueue<? super GCTarget> queue) {
            super(target, queue);
            this.id = target.id;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Finalizing Weak Reference, id: " + id);
        }
    }

    static class WeakReferenceTest {
        // weak reference queue
        private static final ReferenceQueue<GCTarget> QUEUE = new ReferenceQueue<>();

        public static void test() {
            List<MyWeakReference> refs = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                GCTarget target = new GCTarget(i);
                MyWeakReference ref = new MyWeakReference(target, QUEUE);
                refs.add(ref);
                System.out.println("Just created Weak Reference: " + refs.get(refs.size() - 1));
            }

            // notify to GC
            System.gc();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Reference<? extends GCTarget> curr = null;
            while ((curr = QUEUE.poll()) != null) {
                if (curr instanceof MyWeakReference) {
                    System.out.println("Weak Reference " + ((MyWeakReference) curr).id + " has been GCed");
                }
            }
        }
    }

    static class PhantomReferenceTest {
        // phantom reference queue
        private static final ReferenceQueue<String> QUEUE = new ReferenceQueue<>();
        public static void test() {
            String s = new String("abc");
            PhantomReference<String> phantom = new PhantomReference<>(s, QUEUE);

            s = null;
            System.gc();

            System.out.println(phantom.get());
            Reference<? extends String> curr = QUEUE.poll();
            System.out.println(curr);
        }
    }

    public static void main(String[] args) {
        PhantomReferenceTest.test();
    }

}




