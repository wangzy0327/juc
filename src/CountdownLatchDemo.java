import java.util.concurrent.CountDownLatch;

/**
 *
 * 倒计时计数
 *
 */
public class CountdownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
//        countDown();
        joinDemo();
    }

    public static void countDown() throws InterruptedException {
        int num = 6;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        for(int i = 1;i <= num;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println("班长\t离开教室");
    }
    public static void joinDemo(){
        //不推荐
        int num = 6;
        Thread[] threads = new Thread[num];
        for(int i = 1;i <= num;i++){
            threads[i-1] = new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 离开教室");
            },String.valueOf(i));
            threads[i-1].start();
        }
        for(int i = 1;i <= num;i++) {
            try {
                threads[i - 1].join();
                System.out.println(Thread.currentThread().getName()+"\t:\t"+Thread.currentThread().isAlive());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("班长\t离开教室");
    }
}
