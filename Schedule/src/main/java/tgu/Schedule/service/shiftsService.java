package tgu.Schedule.service;

import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.result;

@Service
public interface shiftsService {
    public result getShifts(String date, long sid);
}
