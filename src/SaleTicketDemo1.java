import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket{  //资源类 = 实例变量 + 实例方法
    private int number = 100;

    Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();
        try{
            if(number > 0){
                System.out.println(Thread.currentThread().getName()+"\t 卖出第 "+(number--)+" 张票"+"\t还剩下"+number+"张票");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

/**
 * 题目：三个售票员   卖出  30张票
 * 笔记：如何编写企业级多线程代码
 *   固定的编程套路+模板是什么？
 *   1、在高内聚低耦合的前提下， 线程   操作   资源类
 *      1.1 一言不合 ，先创建一个资源类
 */
public class SaleTicketDemo1 {
    public static void main(String[] args) {
        // 主线程，一切程序的入口
        Ticket ticket = new Ticket();
//        Thread(Runnable target, String name) Allocates a new Thread object.
        //多线程的执行不是start后立刻开始执行，需要等操作系统的调度
        new Thread(()->{for(int i = 1;i <= 400;i++){ ticket.sale(); }},"A").start();
        new Thread(()->{for(int i = 1;i <= 400;i++){ ticket.sale(); }},"B").start();
        new Thread(()->{for(int i = 1;i <= 400;i++){ ticket.sale(); }},"C").start();

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1;i <= 400;i++){
                    ticket.sale();
                }
            }
        },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1;i <= 400;i++){
                    ticket.sale();
                }
            }
        },"B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1;i <= 400;i++){
                    ticket.sale();
                }
            }
        },"C").start();
        */
    }
}
