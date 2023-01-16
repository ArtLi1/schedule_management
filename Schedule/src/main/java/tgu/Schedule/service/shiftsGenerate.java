package tgu.Schedule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.API.dbAccessApi;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.util.DateFormat;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class shiftsGenerate {
    @Resource
    dbAccessApi dbAccessApi;

    public void generate(String date,long sid){

        Shop shop = dbAccessApi.getShop(sid);

        if(shop==null) return;

        //获取本周预测客流量数据
        List<forecast> forecast = getForecast(date, sid);
        if(forecast==null){
            return;
        }

        //获取门店自定义规则
        custom_rules rules = getRules(sid);

        //获取门店员工数据
        List<user_with_preference> userData = getUserData(sid);

        //通过自定义规则和预测客流量生成未填入值班人员班次
        System.out.println(123);



    }

    private List<user_with_preference> getUserData(long sid){
        List<User> userList =getUserList(sid);

        List<user_with_preference> users_data = new ArrayList<>();

        userList.forEach(user -> {
            preferences preference = dbAccessApi.getPreference(user.getId());
            users_data.add(new user_with_preference(user,preference));
        });
        return users_data;
    }


    private custom_rules getRules(long sid){
        custom_rules rules = dbAccessApi.getRules(sid);
        if(rules!=null){
            return rules;
        }
        return new custom_rules();
    }

    private List<forecast> getForecast(String  date, long sid){
        List<forecast> list = new ArrayList<>();
        Date day = DateFormat.getFirstDayOfWeek(date);

        for(int i=0;i<7;i++){
            List<forecast> forecastData = dbAccessApi.getForecastData(DateFormat.ToString(day), sid);
            if(forecastData.size()>0) list.add(forecastData.get(0));
            else{
                return null;
            }
            day = DateFormat.DateAddOneDay(day);
        }

        return list;
    }


    private List<User> getUserList(long sid){
        return dbAccessApi.getUserList(sid);
    }
}

@AllArgsConstructor
@Data
class user_with_preference{
    private User user;
    private preferences preferences;
    private float workDaily=0;
    private float workWeekly=0;

    public user_with_preference(User user, tgu.clwlc.FeignClient.pojo.mongo.preferences preferences) {
        this.user = user;
        this.preferences = preferences;
    }
}
