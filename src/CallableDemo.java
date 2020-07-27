import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyThread implements Runnable{

    @Override
    public void run() {

    }
}

/**
 * Runnable 与 Callable 区别
 * 1 是否有返回值
 * 2 是否抛异常
 * 3 落地方法不一样，一个是run，一个是call
 *
 */
class MyThread2 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("*********callable come in here");
        //暂停一会儿线程
        TimeUnit.SECONDS.sleep(4);
        return 1024;
    }
}

/**
 *
 * 多线程中，第三种获得多线程的方式
 *
 * 1 get方法一般请放在最后一行 （不需要等待时间长的线程结果）
 *
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        MyThread myThread = new MyThread();
//        Thread t1 = new Thread(myThread,"runnable");
//        t1.start();

        //FutureTask 实现了 Runnable接口，并且构造方法接收Callable接口
        FutureTask futureTask = new FutureTask(new MyThread2());

        //多行调用只执行一次
        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();

//        System.out.println(futureTask.get());

        System.out.println(Thread.currentThread().getName()+"******计算完成");

        System.out.println(futureTask.get());
    }
}
