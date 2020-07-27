import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer>{

    private int begin;
    private int end;
    private int result;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    private static final int ADJUST_NUM = 10;

    @Override
    protected Integer compute() {
        if(end - begin <= ADJUST_NUM){
            for(int i = begin;i <= end;i++)
                result += i;
        }else{
            int middle = (end + begin)/2;
            MyTask task01 = new MyTask(begin,middle);
            MyTask task02 = new MyTask(middle+1,end);
            task01.fork();
            task02.fork();
            result = task01.join() + task02.join();
        }
        return result;
    }
}

/**
 *
 * 分支合并框架
 *
 * ForkJoinPool
 * ForkJoinTask  实现 Future<T>
 * RecursiveTask 抽象类
 *
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        try {
            MyTask myTask = new MyTask(0,100);
            ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
            System.out.println(forkJoinTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }
}
