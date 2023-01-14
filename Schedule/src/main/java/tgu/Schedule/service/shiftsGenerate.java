package tgu.Schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.API.dbAccessApi;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;
import tgu.clwlc.FeignClient.pojo.mongo.forecast;
import tgu.clwlc.FeignClient.util.DateFormat;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service

public class shiftsGenerate {
    @Resource
    dbAccessApi dbAccessApi;

    public void generate(String date,long sid){
        forecast forecast = getForecast(date, sid);         //获取预测客流量数据

        custom_rules rules = getRules(sid);                 //获取门店自定义规则


    }

    public custom_rules getRules(long uid){
        custom_rules rules = new custom_rules();
        return rules;
    }

    public forecast getForecast(String  date,long sid){
        return dbAccessApi.getForecastData(date,sid).get(0);
    }

}
