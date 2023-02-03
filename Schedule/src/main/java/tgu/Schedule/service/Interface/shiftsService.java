package tgu.Schedule.service.Interface;

import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

import java.util.List;

@Service
public interface shiftsService {
    public result getShifts(String date, long sid);

    void addShifts(List<secureShifts> shifts);

    void delShifts(long sid, String list);
}
