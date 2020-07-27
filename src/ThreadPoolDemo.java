import java.util.concurrent.*;


/**
 * 最重要的类ThreadPoolExecutor
 * ThreadPoolExecutor(int corePoolSize,
        int maximumPoolSize,
        long keepAliveTime,
        TimeUnit unit,
        BlockingQueue<Runnable> workQueue,
        ThreadFactory threadFactory,
        RejectedExecutionHandler handler)
 *
 * 1.corePoolSize:线程池中的常驻核心线程数
 * 2.maximumPoolSize：线程池中能够容纳同时执行的最大线程数，此值必须大于等于1
 * 3.keepAliveTime:多余的空闲线程的存活时间
 *   当前池中线程数量超过corePoolSize时，当空闲时间达到keepAliveTime时，
 *   多余线程会被销毁直到只剩下corePoolSize为止
 * 4.unit:keepAliveTime的单位
 * 5.workQueue:任务队列，被提交但尚未被执行的任务
 * 6.threadFactory:表示生成线程池中工作线程的线程工厂，用于创建线程，一般默认的即可
 * 7.handler:拒绝策略，表示当队列满了，并且工作线程大于等于线程池的最大线程数（maximumPoolSize）时如何来
 *       拒绝请求执行的runnable的策略
 *
 *
 * 线程池底层工作原理：
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
 *
 * 四大拒绝策略：
 *
 * 1.AbortPolicy（默认）：直接抛出RejectedExecutionException异常阻止系统正常运行
 * 2.CallerRunsPolicy: "调用者运行"一种调节机制，该策略既不会抛弃任务，也不会抛出异常，
 *                     而是将某些任务回退到调用者，从而降低新任务的流量
 * 3.DiscardOldestPolicy：抛弃队列中等待最久的任务，然后把当前任务加入队列中
 *                       尝试再次提交当前任务。
 * 4.DiscardPolicy：该策略默默地丢弃无法处理的任务，不予任何处理也不抛出异常。
 *                  如果任务允许丢失，这是最好的一种策略。
 *
 *
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());

        //maxinumPoolSize设置规则：
        //1、CPU计算密集型 maximumPoolSize = CPU逻辑处理核数+1
        //2.IO密集型  maximumPoolSize = (线程等待时间与线程CPU时间之比 + 1)*CPU核数
        //线程池中可以处理的最大 任务量 = 最大线程数+阻塞队列容量
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

        try {
            //模拟有10个顾客过来银行办理业务，目前池子里面有5个工作人员提供服务
            for(int i = 1;i <= 9;i++){
                threadPool.execute(()->{
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });
            }
        } finally {
            threadPool.shutdown();
        }

//        init();
    }

    //不推荐使用，商业使用不采用任何一种创建方法
    public static void init(){
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5个工作线程，类似一个银行有5个受理窗口
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池1个工作线程，类似一个银行有1个受理窗口
        ExecutorService threadPool = Executors.newCachedThreadPool();//一池有N个工作线程，类似一个银行有N个受理窗口

        try {
            for(int i = 1;i <= 10;i++){
                threadPool.execute(()->{
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });
            }
        } finally {
            threadPool.shutdown();
        }

    }
}
