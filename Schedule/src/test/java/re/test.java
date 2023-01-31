package re;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgu.Schedule.ScheduleApplication;
import tgu.Schedule.service.shiftsGenerate;
import tgu.clwlc.FeignClient.API.dbAccessApi;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;
import tgu.clwlc.FeignClient.pojo.mongo.forecast_data;
import tgu.clwlc.FeignClient.util.DateUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@SpringBootTest(classes = ScheduleApplication.class)
public class test {

    @Autowired
    shiftsGenerate shiftsGenerate;

    @Resource
    dbAccessApi accessApi;

    private final Long TESTSID = 50141025275905L;

    @Test
    public void t1(){
        shiftsGenerate.generate("2021-12-27",TESTSID);
//        System.out.println(DateFormat.getFirstDayOfWeek("2022-1-1"));

    }

    @Test
    public void t2(){
        List<forecast> forecastData = accessApi.getForecastData("2021-12-27", TESTSID);
        Date date = forecastData.get(0).getDate();
        System.out.println(DateUtils.ToString(date));
    }

}
