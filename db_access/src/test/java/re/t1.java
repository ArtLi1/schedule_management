package re;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class t1 {

    @Test
    public void t1() throws IllegalAccessException {
        List<String> list = Arrays.asList("1", "2");
        f1("1","2");
    }

    public void f1(String...s){
        List<String> list = Arrays.asList(s);
        System.out.println(list.size());
    }

    public void update(Object o) throws IllegalAccessException {
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for( Field field : declaredFields){
            System.out.println(field.getName());
            field.setAccessible(true);
            System.out.println(field.get(o));
            field.setAccessible(false);
        }
    }
    public void j1(Object o){
        Class<?> aClass = o.getClass();
    }


}
