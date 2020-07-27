import java.util.Arrays;
import java.util.List;

class User{
    private Integer id;
    private String name;
    private Integer age;

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
public class StreamDemo {
    public static void main(String[] args) {
        User u1 = new User(11,"a",23);
        User u2 = new User(12,"b",24);
        User u3 = new User(13,"c",22);
        User u4 = new User(14,"d",28);
        User u5 = new User(15,"e",26);
        List<User> list = Arrays.asList(u1,u2,u3,u4,u5);
        list.stream()
                .filter(u-> u.getId()%2 == 0)
                .filter(t->t.getAge() > 24)
                .map(m -> m.getName().toUpperCase())
                .sorted((o1,o2)-> o2.compareTo(o1))
                .forEach(System.out::println);

    }
}
