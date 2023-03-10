package tgu.clwlc.FeignClient.pojo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tgu.clwlc.FeignClient.util.DateUtils;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class forecast {
    private long sid;
    private String  date;

    private List<forecast_data> data;

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(Date date) {
        this.date = DateUtils.ToString(date);
    }
}
