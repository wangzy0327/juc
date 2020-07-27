import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *
 * 异步回调
 * CompletableFuture
 *
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //无返回值

        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName()+"没有返回，update mysql ok");
        });
        System.out.println(completableFuture1.get());

        //有返回值

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"\t completableFuture2");
            int age = 10/0;
            return 1024;
        });


        System.out.println(completableFuture2.whenComplete((t, u) -> {
            System.out.println("*****\t" + t);
            System.out.println("*****\t" + u);
        }).exceptionally(f -> {
            System.out.println("*****\t exception:" + f.getMessage());
            return 444;
        }).get());

        System.out.println("hello completableFuture 异步回调");

    }
}
