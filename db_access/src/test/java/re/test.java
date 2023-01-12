package re;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import tgu.clwlc.db_access.dbAccessApplication;

import java.util.List;

@SpringBootTest(classes = dbAccessApplication.class)
public class test {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void t1(){

    }
}
