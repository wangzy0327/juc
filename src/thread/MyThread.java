package thread;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2019\4\23 0023.
 */
public class MyThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//      创建线程的第一种方法
        Thread1 thread1 = new Thread1();
        thread1.start();

//        Thread thread_1 = new Thread(thread1);
//        thread_1.start();

//      创建线程的第二种方法
        Thread2 thread2 = new Thread2();
        Thread thread = new Thread(()->{System.out.println(Thread.currentThread()+"创建线程的第二种方法");});
        thread.start();

//      创建线程的第三种方法
//        Callable<String> callable = new Thread3();
//        FutureTask<String> futureTask = new FutureTask<>(callable);
        FutureTask<String> futureTask = new FutureTask<>(()->{return Thread.currentThread()+"创建线程的第三种方法";});
        Thread thread3 = new Thread(futureTask);
        thread3.start();
        String s = futureTask.get();
        System.out.println(s);

//      创建线程的第四种方法
        Executor executor = Executors.newFixedThreadPool(5);
        executor.execute(()->{
            System.out.println(Thread.currentThread()+"创建线程的第四种方法");
        });
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread()+"创建线程的第四种方法");
//            }
//        });
        ((ExecutorService) executor).shutdown();

    }
}
class Thread1 extends Thread{
    @Override
    public void run() {
        System.out.println(Thread.currentThread()+"创建线程的第一种方法");
    }
}

class Thread2 implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread()+"创建线程的第二种方法");
    }
}

class Thread3 implements Callable<String> {

    @Override
    public String call() throws Exception {
        return Thread.currentThread()+"创建线程的第三种方法";
    }
}