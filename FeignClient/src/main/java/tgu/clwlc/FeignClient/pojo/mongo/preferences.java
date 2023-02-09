package tgu.clwlc.FeignClient.pojo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class preferences {
    private long uid;
    private List<Integer> preferable_days;
    private List<preferable_time> preferable_times;
    private int preferable_duration;


    public preferences(long uid) {
        this.uid = uid;
    }
}
