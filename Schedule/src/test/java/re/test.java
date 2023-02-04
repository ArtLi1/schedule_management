package re;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tgu.Schedule.ScheduleApplication;
import tgu.clwlc.FeignClient.API.dbAccessApi.preferenceApi;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;

import javax.annotation.Resource;


@SpringBootTest(classes = ScheduleApplication.class)
public class test {


    @Resource
    preferenceApi preferenceApi;


    private final Long TESTSID = 50141025275905L;

    @Test
    public void t3(){
        preferences preferences = new preferences(1L,null,null,2333);
        System.out.println(preferenceApi.modifyPreference(preferences));
    }

}
