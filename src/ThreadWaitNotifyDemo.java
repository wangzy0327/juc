import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AirConditioner{//资源类
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public  void increment() throws InterruptedException {
        // 1 判断
        // if不会用来重新判断，while会防止虚假唤醒在线程恢复后而重新进行判断
        /**
         *  wait()
         * As in the one argument version, interrupts and spurious wakeups are possible, and this method should always be used in a loop:
         *
         *      synchronized (obj) {
         *          while (<condition does not hold>)
         *              obj.wait();
         *          ... // Perform action appropriate to condition
         *      }
         *
         */
        lock.lock();
        try {
            // 1 判断
            while(number != 0){
                condition.await();
            }
            // 2 干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            // 3 通知
            condition.signalAll();
        } finally {
           lock.unlock();
        }


    }
    public  void decrement() throws InterruptedException {
        lock.lock();
        try {
            // 1 判断
            while(number == 0){
                condition.await();
            }
            // 2 干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            // 3 通知
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}

/**
 * 题目：现在两个线程，可以操作初始值为0的一个变量，
 * 实现一个线程对该变量加1，一个线程对该变量减1，
 * 实现交替，来10轮，白能量初始值为0
 *
 * 1  高聚低耦前提下，线程操作资源类
 * 2  判断/干活/通知 (wait释放控制权，notify唤醒线程，sleep不释放控制权)
 * 3  多线程交互中，要防止多线程的虚假唤醒，也即（多线程的控制不能用if只能用while）
 */
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        AirConditioner airConditioner = new AirConditioner();
        new Thread(()->{
            for(int i = 1;i <= 10;i++)
            try {
                Thread.sleep(500);
                airConditioner.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

        new Thread(()->{
            for(int i = 1;i <= 10;i++)
                try {
                    Thread.sleep(300);
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        },"B").start();
        new Thread(()->{
            for(int i = 1;i <= 10;i++)
                try {
                    Thread.sleep(400);
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        },"C").start();

        new Thread(()->{
            for(int i = 1;i <= 10;i++)
                try {
                    Thread.sleep(500);
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        },"D").start();
    }
}
