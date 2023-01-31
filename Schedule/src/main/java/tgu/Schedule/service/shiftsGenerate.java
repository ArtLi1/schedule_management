package tgu.Schedule.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.API.dbAccessApi;
import tgu.clwlc.FeignClient.pojo.mongo.*;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;
import tgu.clwlc.FeignClient.util.DateUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class shiftsGenerate {
    @Resource
    dbAccessApi dbAccessApi;

    public void generate(String date, long sid) {

        Shop shop = dbAccessApi.getShop(sid);

        if (shop == null) return;

        //获取本周预测客流量数据
        List<forecast> forecast = getForecast(date, sid);
        if (forecast == null) {
            return;
        }

        //获取门店自定义规则
        custom_rules rules = getRules(sid);

        //获取并处理门店员工数据
        List<user_with_preference> userData = getUserData(sid);

        List<user_with_preference> preferenceUserList = new ArrayList<>();

        Map<User, user_with_preference> userMap = new HashMap<>();

        userData.forEach(u -> {
            if (u.getPreferences() != null) {
                preferenceUserList.add(u);
            }
            userMap.put(u.getUser(), u);
        });

        //通过自定义规则和预测客流量生成未填入值班人员班次
        List<shifts> dutyList = getDutyList(rules, forecast, shop);

        int sum = 0;                //一周总工作时长
        for (shifts shifts : dutyList) {
            for (shift datum : shifts.getData()) {
                sum += datum.getCapacity();
            }
        }
        double avg = (double) sum / userData.size();


        //处理班次填入
        //处理上班前准备班次
        for (shifts shifts : dutyList) {
            //在彻底结束前两小时排入所有需要的班次
            List<shift> shiftData = shifts.getData();
            double endLast = shiftData.get(shiftData.size() - 1).getEnd() - 2;
            int day = DateUtils.getWeekOfDate(shifts.getDate());

            Map<User, Double> duration = new ConcurrentHashMap<>();

            {
                int i = 0;
                for (i = 0; i < shiftData.size(); i++) {
                    shift shift = shiftData.get(i);
                    if (shift.getBegin() >= endLast) break;        //退出 进行最后的班次填入
                    if ((day<6&&shift.getBegin()<9)||(day>=6&&shift.getBegin()<10)) {
                        typeInHandler(shift, rules.getPre(), duration, day, preferenceUserList, userData);
                    }
                    else if((day<6&&shift.getBegin()>=9&&shift.getBegin()<21)||(day>=6&&shift.getBegin()>=10&&shift.getBegin()<22)) {
                        typeInHandler(shift, rules.getMid(), duration, day, preferenceUserList, userData);
                    }else {
                        typeInHandler(shift, rules.getAfter(), duration, day, preferenceUserList, userData);
                    }
                    double interval = shift.getEnd() - shift.getBegin();
                    //没到4小时 则尽量填满至4小时
                    lengthenAll(shifts, i, duration, userMap, day);
                }

                //进行最后的填入
                int n = (int) ((shop.getSize()/rules.getAfter_parameter1())+rules.getAfter_parameter2());
                List<User> l = new ArrayList<>();
                int max = 0;        //所需填入人数
                {
                    int t = i;
                    for(t= i;t<shiftData.size();t++){
                        int t1 = shiftData.get(t).getCapacity()-shiftData.get(t).getList().size();
                        max = Math.max(t1, max);
                    }
                }
                {
                    int t = 0;
                    List<User> typeIn = new ArrayList<>();
                    for (t = 0; t < max; t++) {
                        List<Integer> rule = i == shiftData.size() - 1 ? rules.getAfter() : rules.getMid();
                        shift shift = shiftData.get(i);
                        user_with_preference eligibleUserByAll = getEligibleUserByAll(shift.getBegin(), shift.getEnd(), day, preferenceUserList, rule, duration, shift.getList());
                        if (eligibleUserByAll == null) {
                            eligibleUserByAll = getUserByTime(shift.getBegin(), shift.getEnd(), day, userData, rule, duration, shift.getList());
                        }
                        User user = eligibleUserByAll.getUser();
                        typeIn.add(user);
                        shift.getList().add(user);
                        double time = shift.getEnd() - shift.getBegin();
                        eligibleUserByAll.setWorkWeekly((eligibleUserByAll.getWorkWeekly() + time));
                        eligibleUserByAll.setWorkDaily(eligibleUserByAll.getWorkDaily()[day - 1] + time, day);
                    }
                    i++;
                    for(;i<shiftData.size();i++){
                        for (User user : typeIn) {
                            shift shift = shiftData.get(i);
                            shift.getList().add(user);
                            double time = shift.getEnd() - shift.getBegin();
                            user_with_preference eligibleUserByAll = userMap.get(user);
                            eligibleUserByAll.setWorkWeekly((eligibleUserByAll.getWorkWeekly() + time));
                            eligibleUserByAll.setWorkDaily(eligibleUserByAll.getWorkDaily()[day - 1] + time, day);
                        }
                    }
                }
            }
        }

        List<secureShifts> rs = new ArrayList<>();
        for (shifts shifts : dutyList) {
            rs.add(new secureShifts(shifts));
        }
        removeExistData(rs);
        dbAccessApi.addShifts(rs);
    }


    private void removeExistData(List<secureShifts> rs){
        List<String> dates = new ArrayList<>();
        for (secureShifts r : rs) {
            dates.add(r.getDate());
        }
        dbAccessApi.delShifts(rs.get(0).getSid(),dates);
    }

    private void lengthenAll(shifts shifts, int begin, Map<User, Double> duration, Map<User, user_with_preference> userMap, int day) {
        List<shift> shiftData = shifts.getData();
        shift shift = shiftData.get(begin);
        List<User> Need = new ArrayList<>();
        for (User user : duration.keySet()) {
            if (duration.get(user) > shift.getEnd() - shift.getBegin()) continue;
            Need.add(user);
        }

        for (int i = 0; i < (int) ((2 - (shift.getEnd() - shift.getBegin())) / 0.5); i++) {
            if (begin + i + 1 > shiftData.size() - 1) break;
            tgu.clwlc.FeignClient.pojo.mongo.shift t = shiftData.get(begin + i + 1);
            for (User user : Need) {
                t.getList().add(user);
                duration.put(user, duration.get(user) + t.getEnd() - t.getBegin());
            }

        }


        int n = (int) ((4 - (shift.getEnd() - shift.getBegin())) / 0.5);
        for (int i = (int) ((2 - (shift.getEnd() - shift.getBegin())) / 0.5); i < n; i++) {
            if (begin + i + 1 >= shiftData.size()) return;

            tgu.clwlc.FeignClient.pojo.mongo.shift t_last = shiftData.get(begin + i);
            tgu.clwlc.FeignClient.pojo.mongo.shift t = shiftData.get(begin + i + 1);
            for (User user : Need) {
                if (t.getCapacity() <= t.getList().size()) break;
                user_with_preference ut = userMap.get(user);
                if (t_last.getList().contains(user) && (ut.getWorkDaily(day) < 8 - (t.getEnd() - t.getBegin()) && ut.getWorkWeekly() < 40 - (t.getEnd() - t.getBegin()))) {
                    t.getList().add(user);
                    duration.put(user, duration.get(user) + t.getEnd() - t.getBegin());
                }
            }
        }

    }

    private void typeInHandler(shift shift, List<Integer> rule, Map<User, Double> duration, int day,
                               List<user_with_preference> preferenceUserList, List<user_with_preference> userData) {
        int size = shift.getList().size();
        for (int i = 0; i < shift.getCapacity() - size; i++) {
            user_with_preference eligibleUserByAll = getEligibleUserByAll(shift.getBegin(), shift.getEnd(), day, preferenceUserList, rule, duration, shift.getList());
            if (eligibleUserByAll == null) {
                eligibleUserByAll = getUserByTime(shift.getBegin(), shift.getEnd(), day, userData, rule, duration, shift.getList());
            }
            User user = eligibleUserByAll.getUser();
            shift.getList().add(user);
            double time = shift.getEnd() - shift.getBegin();

            eligibleUserByAll.setWorkWeekly((eligibleUserByAll.getWorkWeekly() + time));
            eligibleUserByAll.setWorkDaily(eligibleUserByAll.getWorkDaily()[day - 1] + time, day);

            double t = duration.get(user) == null ? 0 : duration.get(user);
            duration.put(user, t + shift.getEnd() - shift.getBegin());
        }
        modifyDuration(duration, shift.getList(), shift.getEnd() - shift.getBegin());
    }


    private void modifyDuration(Map<User, Double> duration, List<User> list, double time) {
        Map<User, Double> t = new HashMap<>();

        for (User user : duration.keySet()) {
            if (!list.contains(user)) {
                duration.remove(user);
            }
        }

        list.forEach(u -> {
            if (!t.containsKey(u)) t.put(u, time);
        });
    }

    private user_with_preference getUserByTime(double begin, double end, int day
            , List<user_with_preference> list, List<Integer> rule, Map<User, Double> duration, List<User> userNow) {
        double t2 = 168;
        user_with_preference t = null;
        for (user_with_preference user : list) {

            Double ut = duration.get(user.getUser());

            if (user.getWorkDaily(day) > 6 || user.getWorkWeekly() > 38) continue;

            if (rule == null || !rule.contains(user.getUser().getJob()) && ut == null && !userNow.contains(user.getUser())) {
                if (user.getWorkDaily()[day - 1] < (8 - (end - begin)) && user.getWorkWeekly() < (40 - (end - begin))) {//判断工作时长
                    if (user.getWorkWeekly() < t2) {                      //取最低时长员工
                        t2 = user.getWorkWeekly();
                        t = user;
                    }
                }
            }
        }
        return t;
    }

    private user_with_preference getEligibleUserByAll(double begin, double end, int day
            , List<user_with_preference> list, List<Integer> rule, Map<User, Double> duration, List<User> userNow) {

        double t2 = 168;
        int j = 0;          //以往循环中 j_d的最大值
        user_with_preference t = null;
        for (user_with_preference user : list) {

            if (user.getWorkDaily(day) > 6 || user.getWorkWeekly() > 38) continue;

            Double ut = duration.get(user.getUser());

            if (rule == null || !rule.contains(user.getUser().getJob()) && ut == null && !userNow.contains(user.getUser())) {
                if (user.getWorkDaily()[day] < (8 - (end - begin)) && user.getWorkWeekly() < (40 - (end - begin))) {//判断工作时长
                    if (user.getWorkWeekly() < t2) {                      //取最低时长员工
                        if (user.getPreferences().getPreferable_days().contains(day)) {             //判断偏好日是否含有当天
                            int j_d = 0;        //判断位
                            for (preferable_time time : user.getPreferences().getPreferable_times()) {
                                if (time.getBegin() > begin && time.getEnd() < end) {
                                    j_d = 3;
                                    break;
                                } else if (j_d != 2 && ((time.getBegin() > begin && time.getEnd() > end) || (time.getBegin() < begin && time.getEnd() < end))) {
                                    j_d = 2;
                                } else if (j_d < 1 && time.getBegin() < begin && time.getEnd() > end) {
                                    j_d = 1;
                                }
                            }
                            if (j_d < j) continue;
                            t2 = user.getWorkWeekly();
                            t = user;
                            j = j_d;
                        }
                    }
                }
            }
        }
        return t;
    }


    private List<user_with_preference> getUserData(long sid) {
        List<User> userList = getUserList(sid);

        List<user_with_preference> users_data = new ArrayList<>();

        userList.forEach(user -> {
            preferences preference = dbAccessApi.getPreference(user.getId());
            users_data.add(new user_with_preference(user, preference));
        });
        return users_data;
    }


    private custom_rules getRules(long sid) {
        custom_rules rules = dbAccessApi.getRules(sid);
        if (rules != null) {
            return rules;
        }
        return new custom_rules();
    }

    private List<forecast> getForecast(String date, long sid) {
        List<forecast> list = new ArrayList<>();
        Date day = DateUtils.getFirstDayOfWeek(date);

        for (int i = 0; i < 7; i++) {
            List<forecast> forecastData = dbAccessApi.getForecastData(DateUtils.ToString(day), sid);
            if (forecastData.size() > 0) list.add(forecastData.get(0));
            else {
                return null;
            }
            day = DateUtils.DateAddOneDay(day);
        }

        return list;
    }


    private List<User> getUserList(long sid) {
        return dbAccessApi.getUserList(sid);
    }

    private List<shifts> getDutyList(custom_rules rules, List<forecast> forecast, Shop shop) {

        List<shifts> list = new ArrayList<>();

        forecast.forEach(f -> {
            shifts s = shifts.emptyShiftsGenerator(f, rules, shop.getSize());
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
    private double workWeekly=0;

    public void setWorkDaily(double[] workDaily) {
        this.workDaily = workDaily;
    }

    public void setWorkDaily(double workDaily,int day) {
        this.workDaily[day-1] = workDaily;
    }


    public double[] getWorkDaily() {
        return workDaily;
    }

    public double getWorkDaily(int day) {
        return workDaily[day-1];
    }

    public user_with_preference(User user, tgu.clwlc.FeignClient.pojo.mongo.preferences preferences) {
        this.user = user;
        this.preferences = preferences;
    }
}
