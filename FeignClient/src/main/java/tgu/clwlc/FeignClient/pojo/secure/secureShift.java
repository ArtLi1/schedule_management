package tgu.clwlc.FeignClient.pojo.secure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tgu.clwlc.FeignClient.pojo.mongo.shift;
import tgu.clwlc.FeignClient.pojo.mysql.User;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class secureShift {

    double begin;
    double end;
    int capacity=0;
    List<secureUser> list;

    public secureShift(shift shift){
        this.begin = shift.getBegin();
        this.end = shift.getEnd();
        this.capacity = shift.getCapacity();
        list = new ArrayList<>();
        for (User user : shift.getList()) {
            list.add(new secureUser(user));
        }
    }

}
