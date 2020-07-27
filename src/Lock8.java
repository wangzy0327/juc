import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

class Phone{
    public static synchronized void sendEmail(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----sendEmail");
    }
    public synchronized void sendSMS(){
        System.out.println("-----sendSMS");
    }
    public void hello(){
        System.out.println("------hello!");
    }
}


/**
 * 1 标准访问  打印邮件还是短信？
 * 2 邮件方法暂停4秒钟，请问先打印邮件还是短信?
 * 3 新增一个普通方法hello()，请问先打印邮件还是hello?
 * 4 两部手机，请问先打印邮件还是短信？
 * 5 两个静态同步方法，同一部手机，请问先打印邮件还是短信
 * 6 两个静态同步方法，2部手机，请问先打印邮件还是短信
 * (Class锁跟this锁不是同一个对象，不存在竞争关系)
 * 7 1个普通同步方法，1个静态同步方法，1部手机，请问先打印邮件还是短信
 * 8 1个普通同步方法，1个静态同步方法，2部手机，请问先打印邮件还是短信
 */
public class Lock8 {
    public static void main(String[] args) {
        Phone phone = new Phone();
//        Phone phone2 = new Phone();
        new Thread(()->{phone.sendEmail();},"邮件").start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{phone.sendSMS();},"短信").start();
//        new Thread(()->{phone.hello();},"hello").start();
        List<String> list = Arrays.asList("456","123");
        list.stream().forEach(
                System.out::print);
    }
}
