package re;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgu.Schedule.ScheduleApplication;
import tgu.Schedule.service.shiftsGenerate;
import tgu.clwlc.FeignClient.util.DateFormat;

import java.util.Date;


@SpringBootTest(classes = ScheduleApplication.class)
public class test {

    @Autowired
    shiftsGenerate shiftsGenerate;

    @Test
    public void t1(){

    }
}
