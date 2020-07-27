/**
 * 1  函数式编程
 *    int age = 23;
 * 1  拷贝小括号，写死右箭头，落地大括号
 * 2  @FunctionalInterface
 * 3  default
 * 4  static
 */
@FunctionalInterface
interface Foo{
    int add(int x,int y);
    default int multiply(int x,int y){
        return x * y;
    }
}
public class LambdaExpressDemo02 {
    public static void main(String[] args) {
        Foo f = (int x,int y)->{
            System.out.println("come in add method");
            return x + y;
        };
        System.out.println(f.add(3,5));
    }
}
