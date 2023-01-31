package re;

import lombok.Data;
import tgu.clwlc.FeignClient.pojo.mysql.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test1 {

    public static void main(String[] args) {
        Map<t1 ,String > m = new HashMap<>();
        t1 t = new t1();
        m.put(t,"123");
        for (t1 t1 : m.keySet()) {
            System.out.println(t1);
        }
    }
}
@Data
class t1{
    public int a = 1;
    public String a1;
    public int hashCode(){
        return 1;
    }

    @Override
    public String toString() {
        return "t1{" +
                "a=" + a +
                ", a1='" + a1 + '\'' +
                '}';
    }
}
