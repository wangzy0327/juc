import java.util.concurrent.*;

//ThreadPoolExecutor(int corePoolSize,
//        int maximumPoolSize,
//        long keepAliveTime,
//        TimeUnit unit,
//        BlockingQueue<Runnable> workQueue,
//        ThreadFactory threadFactory,
//        RejectedExecutionHandler handler)

/**
 * ThreadPoolExecutor 线程池七大参数：
 * 1、corePoolSize:线程池中的常驻贺信线程数
 * 2、maximumPoolSize:线程池中能够容纳同时执行的最大线程数，此值必须大于等于1
 * 3、keepAliveTime:多余的空闲线程的存活时间，当线程池中线程数量超过corePoolSize时
 *   当空闲时间达到keepAliveTime时，多余线程会被销毁知道只剩下corePoolSize个线程为止
 * 4、unit:keepAliveTime的单位
 * 5、workQueue:任务队列，被提交但尚未执行的任务
 * 6、threadFactory：表示生成线程池中工作线程的线程工厂，一般默认的即可
 * 7、handler：拒绝策略，表示当队列满了，并且工作线程大于等于
 *   线程池的最大线程数（maxinumPoolSize）时如何来拒绝请求执行的runnable的策略
 *
 */
/*
线程池底层工作原理：
*   1.提交任务
*   2.核心线程数是否已满，否 创建线程执行任务
*   3.是 队列是否已满  否 将任务存储在队列中
*   4.是 线程池是否已经满（达到最大线程数）  否 创建线程执行任务
*   5.是 按照策略处理无法执行的任务
*
* 【强制】线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式
* 说明：Executors返回的线程池对象弊端如下：
* 1.FixedThreadPool和SingleThreadPool：
*    允许的请求队列长度为Integer.MAX_VALUE,可能会堆积大量的请求，从而导致OOM
* 2.CachedThreadPool和ScheduledThreadPool：
*    允许的创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM
 */
/***
 *
 * 工作原理：
 * 1.核心线程数未满，核心线程数先处理
 * 2.核心线程数已满，进入阻塞队列
 * 3.阻塞队列已满，扩容到最大线程数
 * 4.最大线程数跟阻塞队列如果都满了，直接拒绝策略
 *
 * 拒绝策略：
 *
 *  AbortPolicy（默认）:直接抛出RejectedExecutionException异常组织系统正常运行
 *  CallerRunsPolicy：“调用者运行”一种调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将任务回退，
 *                     从而降低新任务的流量
 *  DiscardOldestPolicy:抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务
 *  DiscardPolicy：该策略默默地丢弃无法处理的任务，不予任何处理也不抛出异常。
 *                 如果允许任务丢失，这是最好的一种策略。
 *
 *
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        //线程池最大容纳数 ：maximumPoolSize + queue capacity
        //maxinumPoolSize设置规则：
        //1、CPU计算密集型 maximumPoolSize = CPU逻辑处理核数+1
        //2.IO密集型  maximumPoolSize = (线程等待时间与线程CPU时间之比 + 1)*CPU核数
        //线程池中可以处理的最大 任务量 = 最大线程数+阻塞队列容量
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            for(int i = 1;i <= 10;i++){
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    //在真实生成过程中都采用new ThreadPoolExecutor  以下都不使用
    public static void init(){
        //        ExecutorService executorService = Executors.newFixedThreadPool(5); //一池5个工作线程，相当于银行有5个柜台
//        ExecutorService executorService = Executors.newSingleThreadExecutor(); //一池1个工作线程，相当于银行有1个柜台
//        ExecutorService executorService = Executors.newCachedThreadPool();
        //执行很多短期异步任务，线程池根据需要创建新线程，但在先前构建的线程可用时将重用它们，可扩容
        //一池N个工作线程，相当于银行有N个柜台

//        try {
//            for(int i = 1;i <= 10;i++){
//                executorService.execute(()->{
//                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            executorService.shutdown();
//        }
    }
}
