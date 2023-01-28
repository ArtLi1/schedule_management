package tgu.Schedule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.API.dbAccessApi;
import tgu.clwlc.FeignClient.pojo.mongo.*;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.util.DateUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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

        //获取并处理门店员工数据
        List<user_with_preference> userData = getUserData(sid);

        List<user_with_preference> preferenceUserList = new ArrayList<>();
        List<user_with_preference> noPreferenceUserList = new ArrayList<>();

        userData.forEach(u->{
            if(u.getPreferences()==null){
                noPreferenceUserList.add(u);
            }else {
                preferenceUserList.add(u);
            }
        });

        //通过自定义规则和预测客流量生成未填入值班人员班次
        List<shifts> dutyList = getDutyList(rules, forecast,shop);

        int sum = 0;                //一周总工作时长
        for (shifts shifts : dutyList) {
            for (shift datum : shifts.getData()) {
                sum+=datum.getCapacity();
            }
        }
        double avg = (double) sum / userData.size();



        //处理班次填入
        //处理上班前准备班次
        for (shifts shifts : dutyList) {
            //在彻底结束前两小时排入所有需要的班次
            List<shift> data = shifts.getData();
            double endLast = data.get(data.size()-1).getEnd()-2;
            int day = DateUtils.getWeekOfDate(shifts.getDate());



        }

    }

    private user_with_preference getEligibleUserByAll(double begin,double end,int day
            ,List<user_with_preference> list,List<Integer> rule){

        double t1 = 24;
        double t2 = 168;
        int j = 0;          //以往循环中 j_d的最大值
        user_with_preference t = null;
        for (user_with_preference user : list) {
            if(user.getWorkDaily()[day]<(8-(end-begin))&&user.getWorkWeekly()<(40-(end-begin))){//判断工作时长
                if(user.getWorkDaily()[day]<t1&&user.getWorkWeekly()<t2) {                      //取最低时长员工
                    if (user.getPreferences().getPreferable_days().contains(day)) {             //判断偏好日是否含有当天
                        int j_d = 0;        //判断位
                        for (preferable_time time : user.getPreferences().getPreferable_times()) {
                            if(time.getBegin()>begin&&time.getEnd()<end){
                                j_d = 3;
                                break;
                            }else if(j_d!=2&&((time.getBegin()>begin&&time.getEnd()>end)||(time.getBegin()<begin&&time.getEnd()<end))){
                                j_d = 2;
                            }else if(j_d<1&&time.getBegin()<begin&&time.getEnd()>end){
                                j_d = 1;
                            }
                        }

                        if(j_d < j) continue;
                        t1 = user.getWorkDaily()[day];
                        t2 = user.getWorkWeekly();
                        t = user;
                        j =j_d;
                    }
                }
            }
        }
        return t;
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
        Date day = DateUtils.getFirstDayOfWeek(date);

        for(int i=0;i<7;i++){
            List<forecast> forecastData = dbAccessApi.getForecastData(DateUtils.ToString(day), sid);
            if(forecastData.size()>0) list.add(forecastData.get(0));
            else{
                return null;
            }
            day = DateUtils.DateAddOneDay(day);
        }

        return list;
    }


    private List<User> getUserList(long sid){
        return dbAccessApi.getUserList(sid);
    }

    private List<shifts> getDutyList(custom_rules rules, List<forecast> forecast,Shop shop) {

        List<shifts> list = new ArrayList<>();

        forecast.forEach(f -> {
            shifts s = shifts.shiftsGenerator(f,rules,shop.getSize());
            list.add(s);
        });

        return list;
    }

}

@AllArgsConstructor
@Data
class user_with_preference{
    private User user;
    private preferences preferences;
    private double[] workDaily = {0f, 0f, 0f, 0f, 0f, 0f, 0f};
    private float workWeekly=0;



    public user_with_preference(User user, tgu.clwlc.FeignClient.pojo.mongo.preferences preferences) {
        this.user = user;
        this.preferences = preferences;
    }
}
