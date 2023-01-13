package tgu.clwlc.db_access.pojo.mongo;

import lombok.Data;

import java.util.List;

@Data
public class custom_rules {
    private long id;
    private float pre_time = 1;                     //开店前准备时间
    private float pre_parameter = 100;              //开店前所需人数为店面size/该参数
    private float parameter = 3.8f;                 //某个时间段中所需人数为预测客流量/该参数
    private int empty_num = 1;                      //客流量为0时所需要的人数
    private float after_time = 2;                   //关店后准备时间
    private float after_parameter1 = 80;            //关店后所需人数为 (面积/p1)+p2
    private float after_parameter2 = 1;
    private List<Integer> pre;                      //不执行对应活动的职位
    private List<Integer> mid;
    private List<Integer> after;
}
