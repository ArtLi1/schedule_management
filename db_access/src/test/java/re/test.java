package re;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import tgu.clwlc.db_access.dbAccessApplication;
import tgu.clwlc.db_access.pojo.shift;
import tgu.clwlc.db_access.pojo.shifts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = dbAccessApplication.class)
public class test {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void t1(){
        shifts shift = new shifts();
        shift.setDate(new Date());
        List<tgu.clwlc.db_access.pojo.shift> list = new ArrayList<>();
        list.add(new shift(2.5F, 3.5F,2));
        list.add(new shift(1.5F,3.5F,3));
        shift.setData(list);
        mongoTemplate.insert(shift);
    }
    @Test
    public void t2(){
        System.out.println(mongoTemplate.findAll(shifts.class).get(1).toString());
    }
}
