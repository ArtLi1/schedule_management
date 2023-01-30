package tgu.clwlc.FeignClient.pojo.mongo;


import lombok.Data;
import tgu.clwlc.FeignClient.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class shifts {

    private long sid;

    private Date date;

    private List<shift> data;

    public void setDate(Date date) {
        this.date = DateUtils.ToDate(date);
    }

    public void setDate(String date) {
        this.date = DateUtils.ToDate(date);
    }

    public shifts(long sid, Date date) {
        this.sid = sid;
        this.date = date;
    }

    public static shifts emptyShiftsGenerator(forecast forecast, custom_rules rule, double size){
        shifts s = new shifts(forecast.getSid(),forecast.getDate());

        int day = DateUtils.getWeekOfDate(forecast.getDate());
        int start = day<6?9:10;
        int end = day<6?21:22;

        int preStart = (int) (start-rule.getPre_time());
        int pre_capacity = (int) (size%rule.getPre_parameter()==0?size/rule.getPre_parameter():size/rule.getPre_parameter()+1);

        int afterEnd = (int) (end+rule.getAfter_time());
        int after_capacity = (int)((size/rule.getAfter_parameter1())+rule.getAfter_parameter2());

        List<shift> list = new ArrayList<>();


        for(int i=0;i<(start-preStart)/0.5;i++){
            list.add(new shift(preStart+i*0.5,preStart+(i+1)*0.5,pre_capacity));
        }

        if(day>5) {
            forecast.getData().forEach(f -> {
                if(f.getBegin()>=10&&f.getBegin()<22){
                    list.add(new shift(f, rule.getParameter()));
                }
            });
        }else {
            forecast.getData().forEach(f -> {
                if(f.getBegin()>=9&&f.getBegin()<21){
                    list.add(new shift(f, rule.getParameter()));
                }
            });
        }

        for(int i=0;i<(afterEnd-end)/0.5;i++){
            list.add(new shift(end+i*0.5,end+(i+1)*0.5,pre_capacity));
        }

        s.setData(list);
        return s;
    }
}

