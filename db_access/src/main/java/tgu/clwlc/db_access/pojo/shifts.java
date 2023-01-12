package tgu.clwlc.db_access.pojo;


import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class shifts {
    private Date date;
    private List<shift> data;

    public void setDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = simpleDateFormat.parse(simpleDateFormat.format(date));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setDate(String  date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");
        try {
            this.date = simpleDateFormat.parse(date);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

