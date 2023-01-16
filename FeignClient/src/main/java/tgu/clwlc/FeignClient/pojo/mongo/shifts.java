package tgu.clwlc.FeignClient.pojo.mongo;


import lombok.Data;
import tgu.clwlc.FeignClient.util.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class shifts {

    private long sid;

    private Date date;

    private List<shift> data;

    public void setDate(Date date) {
        this.date = DateFormat.ToDate(date);
    }

    public void setDate(String date) {
        this.date = DateFormat.ToDate(date);
    }
}

