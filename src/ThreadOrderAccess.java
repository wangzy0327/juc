import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource{
    private int number = 1;  //1:A 2:B 3:C
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    public Condition getCondition1() {
        return condition1;
    }

    public Condition getCondition2() {
        return condition2;
    }

    public Condition getCondition3() {
        return condition3;
    }

    public class P{
        private int number;
        private Condition c1;
        private Condition c2;
        public  P(int number,Condition condition1,Condition condition2){
            this.number = number;
            this.c1 = condition1;
            this.c2 = condition2;
        }
    }

    public void print(ShareResource.P p){
        lock.lock();
        try {
            // 1 判断

            while(number != p.number){
                if(p.c1 == condition1)
                    condition1.await();
                else if(p.c1 == condition2)
                    condition2.await();
                else if(p.c1 == condition3){
                    condition3.await();
                }
            }
            // 2 干活
            for(int i = 1;i <= 5*p.number ;i++)
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            number = (number + 1)%3;
            if(p.c2 == condition1)
                condition1.signal();
            else if(p.c2 == condition2)
                condition2.signal();
            else if(p.c2 == condition3){
                condition3.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print5(){
        lock.lock();
        try {
            // 1 判断
            while(number != 1){
                condition1.await();
            }
            // 2 干活
            for(int i = 1;i <= 5 ;i++)
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            number = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            // 1 判断
            while(number != 2){
                condition2.await();
            }
            // 2 干活
            for(int i = 1;i <= 10 ;i++)
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            number = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            // 1 判断
            while(number != 3){
                condition3.await();
            }
            // 2 干活
            for(int i = 1;i <= 15 ;i++)
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            number = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

/**
 *
 * 多线程之间顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * ......来10轮
 *
 *  1  高聚低耦前提下，线程操作资源类
 *  2  判断/干活/通知 (wait释放控制权，notify唤醒线程，sleep不释放控制权)
 *  3  多线程交互中，要防止多线程的虚假唤醒，也即（多线程的控制不能用if只能用while）
 *  4  注意标志位的修改和定位
 */
public class ThreadOrderAccess {
    public static void main(String[] args) {
        ShareResource sr = new ShareResource();
        new Thread(()->{
            sr.print5();
        },"A").start();
        new Thread(()->{
            sr.print10();
        },"B").start();
        new Thread(()->{
            sr.print15();
        },"C").start();
    }
}
