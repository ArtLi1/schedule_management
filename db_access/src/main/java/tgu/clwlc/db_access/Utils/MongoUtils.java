package tgu.clwlc.db_access.Utils;


import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
public class MongoUtils {



    public static Update update(Object o,String...Key) throws IllegalAccessException {
        List<String> list = Arrays.asList(Key);
        Update update = new Update();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if(isContain(list,field.getName())) continue;
            field.setAccessible(true);
            Object value = field.get(o);
            if(value==null) continue;
            update.set(field.getName(),value);
            field.setAccessible(false);
        }
        return update;
    }

    public static boolean isContain(List<String> list,String key){
        for (String s : list) {
            if(s.equals(key)) return true;
        }
        return false;
    }

}
