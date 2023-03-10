package re;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;
import tgu.clwlc.FeignClient.util.DateUtils;
import tgu.clwlc.db_access.Controller.ForecastController;
import tgu.clwlc.db_access.dao.PermissionMapper;
import tgu.clwlc.db_access.dao.UserWithPMapper;
import tgu.clwlc.db_access.dbAccessApplication;

import javax.annotation.Resource;

@SpringBootTest(classes = dbAccessApplication.class)
public class t1 {

    @Resource
    UserWithPMapper userPMapper;

    @Resource
    PermissionMapper permissionMapper;

    @Resource
    ForecastController forecastController;

    @Resource
    MongoTemplate mongoTemplate;
    @Test
    public void t1() {
        System.out.println(forecastController.getForecastData("2023-03-06", 50141025275905L));
    }

}
