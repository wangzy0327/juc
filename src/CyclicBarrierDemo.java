import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * 七科龙珠召唤神龙（人到齐了开会）
 *
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int num = 7;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num,()->{
            System.out.println("召唤神龙");
        });
        for(int i = 1;i <= num;i++){
            final int numI = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t集齐第"+numI+"颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
