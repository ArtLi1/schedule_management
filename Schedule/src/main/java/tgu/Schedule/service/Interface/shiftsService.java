package tgu.Schedule.service.Interface;

import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.result;

@Service
public interface shiftsService {
    result getShifts(String date, long sid);


    result delShifts(long sid, String date);
}
