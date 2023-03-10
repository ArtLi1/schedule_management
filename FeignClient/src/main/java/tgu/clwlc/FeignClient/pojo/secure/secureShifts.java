package tgu.clwlc.FeignClient.pojo.secure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tgu.clwlc.FeignClient.pojo.mongo.shift;
import tgu.clwlc.FeignClient.pojo.mongo.shifts;
import tgu.clwlc.FeignClient.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class secureShifts {
    private long sid;

    private String  date;

    private List<secureShift> data;


    public secureShifts(shifts shifts){
        this.sid = shifts.getSid();
        this.date = shifts.getDate();
        data = new ArrayList<>();
        for (shift datum : shifts.getData()) {
            data.add(new secureShift(datum));
        }
    }

    public secureShifts(long sid, String date) {
        this.sid = sid;
        this.date = date;
    }
}
