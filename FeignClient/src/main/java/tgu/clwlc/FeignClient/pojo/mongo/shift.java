package tgu.clwlc.FeignClient.pojo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.secure.secureUser;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class shift {
    double begin;
    double end;
    int capacity=0;
    List<User> list;

    public shift(double begin, double end) {
        this.begin = begin;
        this.end = end;
        list = new ArrayList<>();
    }

    public shift(double begin, double end, int capacity) {
        this.begin = begin;
        this.end = end;
        this.capacity = capacity;
        list = new ArrayList<>();
    }

    public shift(double begin, double end, double forecast_data, double x1) {
        this.begin = begin;
        this.end = end;
        this.capacity = (int) (forecast_data%x1==0?forecast_data/x1:forecast_data/x1+1);
        list = new ArrayList<>();
    }

    public shift(forecast_data data,double x){
        begin = data.getBegin();
        end = data.getEnd();
        capacity = (int) (data.getData()%x==0?data.getData()/x:data.getData()/x+1);
        list = new ArrayList<>();
    }
}
