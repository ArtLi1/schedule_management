package tgu.clwlc.db_access.Utils;


import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MongoUtils {

    @Resource
    MongoTemplate mongoTemplate;

    private static final Map<Class<?>,List<String>> map = new HashMap<>();

    static {
        map.put(custom_rules.class, List.of("sid"));
        map.put(forecast.class,Arrays.asList("sid","date"));
        map.put(preferences.class, List.of("uid"));
        map.put(secureShifts.class,Arrays.asList("sid","date"));
    }

    public Object Modify(Object o , FindAndModifyOptions options) throws IllegalAccessException {
        return Modify(o,options, map.get(o.getClass()));
    }

    public Object Modify(Object o , FindAndModifyOptions options, String ...key) throws IllegalAccessException {
        return Modify(o,options,Arrays.asList(key));
    }

    public Object Modify(Object o , FindAndModifyOptions options, List<String> Key) throws IllegalAccessException {
        Assert.notEmpty(Key,"Key cannot be empty");
        Criteria criteria = new Criteria();
        Update update = update(o,Key);
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(isContain(Key,field.getName())){
                criteria =  criteria.and(field.getName()).is(field.get(o));
            }
            field.setAccessible(false);
        }
        return mongoTemplate.findAndModify(new Query(criteria), update, options, o.getClass());
    }


    public long remove(Object O, List<String> Key) throws IllegalAccessException{
        Assert.notEmpty(Key,"Key is Empty");
        Criteria criteria = new Criteria();
        for (Field field : O.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(isContain(Key,field.getName())){
                criteria =  criteria.and(field.getName()).is(field.get(O));
            }
            field.setAccessible(false);
        }
        return mongoTemplate.remove(new Query(criteria), O.getClass()).getDeletedCount();

    }

    public long remove(Object O,String ... Key) throws IllegalAccessException {
        return remove(O,Arrays.asList(Key));
    }

    public static Update update(Object o,List<String> Key) throws IllegalAccessException{
        Assert.notEmpty(Key,"Key is Empty");
        Update update = new Update();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if(isContain(Key,field.getName())) continue;
            field.setAccessible(true);
            Object value = field.get(o);
            if(value!=null) update.set(field.getName(),value);
            field.setAccessible(false);
        }
        return update;
    }

    public static Update update(Object o,String...Key) throws IllegalAccessException {
      return update(o,Arrays.asList(Key));
    }

    public static boolean isContain(List<String> list,String key){
        for (String s : list) {
            if(s.equals(key)) return true;
        }
        return false;
    }

    public static FindAndModifyOptions defaultOptions(){
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.remove(true).returnNew(true).upsert(false);
        return options;
    }

    public static FindAndModifyOptions options(boolean remove,boolean upsert,boolean returnNew){
        return new FindAndModifyOptions().returnNew(returnNew).upsert(upsert).remove(remove);
    }

}
