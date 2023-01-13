package re;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import tgu.clwlc.db_access.dbAccessApplication;
import tgu.clwlc.db_access.pojo.mongo.shift;
import tgu.clwlc.db_access.pojo.mongo.shifts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        shift.setDate("2022-3-5");
        List<tgu.clwlc.db_access.pojo.mongo.shift> list = new ArrayList<>();
        list.add(new shift(2.5F, 3.5F,2));
        list.add(new shift(1.5F,3.5F,3));
        shift.setData(list);
        mongoTemplate.insert(shift);
    }
    @Test
    public void t2(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            List<shifts> date = mongoTemplate.find(new Query(Criteria.where("date").is(simpleDateFormat.parse("2022-3-5"))), shifts.class);
            date.forEach(System.out::println);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
