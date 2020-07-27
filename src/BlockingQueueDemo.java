import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *阻塞队列
 *
 * 1 两个数据结构：栈/队列
 * 1.1 栈  后进先出
 * 1.2 队列 先进先出
 * 1.3 总结
 *
 * 2  阻塞队列
 * 2.1 阻塞  必须要阻塞/不得不阻塞
 *    interface : BlockingQueue
 *
 *   ArrayBlockingQueue:由数组结构组成的有界阻塞队列
 *   LinkedBlokingQueue:由链表结构组成的有界（但大小默认值为Integer.MAX_VALUE）阻塞队列
 *   SynchronousQueue:不存储元素的阻塞队列，也即单个元素的队列
 *
 *   PriorityBlockingQueue:支持优先级排序的无界阻塞队列
 *   DelayQueue:使用优先级队列实现的延迟无界阻塞队列
 *   LinekedTransferQueue:由链表组成的无界阻塞队列
 *   LinkedBlockingDeque:由链表组成的双向阻塞队列(双端队列)
 *
 * 3 how
 */

public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(3);

        /* 第一种
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("a"));  IllegalStateException

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());  NoSuchElementException

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.element());   //队首

        */

        /* 第二种方法
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("d"));  返回false

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());    //返回null

        System.out.println(blockingQueue.peek());

         */

        /* 第三种
        // put take 会一直阻塞  (不见不散)
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");
//        blockingQueue.put("a");    //一直阻塞 最大只能装3个

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());  // 阻塞 一直等待
        */

        //第四种 超时等待（过时不候）
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("b"));
//        System.out.println(blockingQueue.offer("a",3L,TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));


    }
}
