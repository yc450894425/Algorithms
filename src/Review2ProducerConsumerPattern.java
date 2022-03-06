import java.util.ArrayDeque;
import java.util.Queue;

//  The standard idiom for calling the wait method in Java
//    synchronized (sharedObject) {
//        while (condition) {
//            sharedObject.wait(); // releases lock, and will require on wakeup
//        }
//        // do action based upon condition e.g. take or put into queue
//    }

public class Review2ProducerConsumerPattern {
    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayDeque<>();
        Producer producer = new Producer(queue, 1);
        Consumer consumer = new Consumer(queue);
        producer.start();
        consumer.start();
    }
}

class Producer extends Thread {
    private Queue<Integer> queue;
    private int maxSize;

    public Producer(Queue<Integer> queue, int maxSize) {
        super();
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.size() == maxSize) {
                    System.out.println("Queue is full. Producer starts waiting.");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int random = (int) (Math.random() * 100 + 1);
                System.out.println("Producer produces " + random);
                queue.offer(random);
                queue.notifyAll();
            }
        }
    }
}

class Consumer extends Thread {
    private Queue<Integer> queue;

    public Consumer(Queue<Integer> queue) {
        super();
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty. Consumer starts waiting.");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Consumer consumes " + queue.poll());
                queue.notifyAll();
            }
        }
    }
}
