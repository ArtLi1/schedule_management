package tgu.clwlc.FeignClient.pojo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tgu.clwlc.FeignClient.util.DateFormat;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class forecast {
    private long sid;
    private Date date;

    private List<forecast_data> data;

    public void setDate(String date) {
        this.date = DateFormat.ToDate(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
