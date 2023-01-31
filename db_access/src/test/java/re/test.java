package re;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;
import tgu.clwlc.FeignClient.pojo.mongo.forecast_data;
import tgu.clwlc.FeignClient.util.SnowflakeIdGenerate;
import tgu.clwlc.db_access.Controller.ForecastController;
import tgu.clwlc.db_access.Controller.ShiftsController;
import tgu.clwlc.db_access.Controller.UserController;
import tgu.clwlc.db_access.dbAccessApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = dbAccessApplication.class)
public class test {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ForecastController forecastController;

    @Autowired
    UserController userController;

    SnowflakeIdGenerate idGenerate = new SnowflakeIdGenerate(888);

    private final Long TESTSID = 50141025275905L;


    @Autowired
    ShiftsController shiftsController;

    @Test
    public void t() {

        {//用户数据
//            User user1 = new User(idGenerate.nextId(),50141025275905L,"u0","email@q.c",1,152,"123");
//            for(int i=0;i<15;i++){
//            user1.setEmail(user1.getEmail()+"1");
//            userController.addUser(user1);
//            user1.setId(idGenerate.nextId());
//            user1.setName("u"+(i+1));
//            }
        }


        {//预测数据
        forecast forecast = new forecast();
        forecast.setDate("2022-1-2");
        forecast.setSid(50141025275905L);
        forecast_data data1 = new forecast_data(8, 8.5f, 0);
        forecast_data data2 = new forecast_data(8.5f, 9, 0.1f);
        forecast_data data3 = new forecast_data(9, 9.5f, 1.3f);
        forecast_data data4 = new forecast_data(9.5f, 10, 5.7f);
        forecast_data data5 = new forecast_data(10, 10.5f, 11.1f);
        forecast_data data6 = new forecast_data(10.5f, 11, 13.4f);
        forecast_data data7 = new forecast_data(11, 11.5f, 13.3f);
        forecast_data data8 = new forecast_data(11.5f, 12, 17.3f);
        forecast_data data9 = new forecast_data(12, 12.5f, 18.1f);
        forecast_data data10 = new forecast_data(12.5f, 13, 22.8f);
        forecast_data data11 = new forecast_data(13, 13.5f, 26.9f);
        forecast_data data12 = new forecast_data(13.5f, 14, 21.6f);
        forecast_data data13 = new forecast_data(14, 14.5f, 18.3f);
        forecast_data data14 = new forecast_data(14.5f, 15, 17.2f);
        forecast_data data15 = new forecast_data(15f, 15.5f, 15.3f);
        forecast_data data16 = new forecast_data(15.5f, 16, 14.3f);
        forecast_data data17 = new forecast_data(16, 16.5f, 11.6f);
        forecast_data data18 = new forecast_data(16.5f, 17, 8.3f);
        forecast_data data19 = new forecast_data(17, 17.5f, 8.3f);
        forecast_data data20 = new forecast_data(17.5f, 18, 7.2f);
        forecast_data data21 = new forecast_data(18, 18.5f, 5.6f);
        forecast_data data22 = new forecast_data(18.5f, 19, 5.6f);
        forecast_data data23 = new forecast_data(19, 19.5f, 2.5f);
        forecast_data data24 = new forecast_data(19.5f, 20, 2.1f);
        forecast_data data25 = new forecast_data(20, 20.5f, 0.1f);
        forecast_data data26 = new forecast_data(20.5f, 21, 0.1f);
        List<forecast_data> data = new ArrayList<>();
        data.add(data1);
        data.add(data2);
        data.add(data3);
        data.add(data4);
        data.add(data5);
        data.add(data6);
        data.add(data7);
        data.add(data8);
        data.add(data9);
        data.add(data10);
        data.add(data11);
        data.add(data12);
        data.add(data13);
        data.add(data14);
        data.add(data15);
        data.add(data16);
        data.add(data17);
        data.add(data18);
        data.add(data19);
        data.add(data20);
        data.add(data21);
        data.add(data22);
        data.add(data23);
        data.add(data24);
        data.add(data25);
        data.add(data26);
        forecast.setData(data);
        forecastController.addData(forecast);
//        forecast.setDate("2021-12-27");
//        forecastController.addData(forecast);
//            forecast.setDate("2021-12-28");
//            forecastController.addData(forecast);
//            forecast.setDate("2021-12-29");
//            forecastController.addData(forecast);
//            forecast.setDate("2021-12-30");
//            forecastController.addData(forecast);
//            forecast.setDate("2021-12-31");
//            forecastController.addData(forecast);
//            forecast.setDate("2022-1-1");
//            forecastController.addData(forecast);
        }
    }
    @Test
    public void t2() {
        System.out.println(shiftsController.getShifts(TESTSID, "2021-12-27"));
    }
}
