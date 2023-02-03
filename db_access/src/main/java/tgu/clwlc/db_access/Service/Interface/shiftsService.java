package tgu.clwlc.db_access.Service.Interface;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

import java.util.Date;
import java.util.List;

@Service
public interface shiftsService {

    public secureShifts getShifts(long sid, String date);

    public void addShifts(List<secureShifts> shifts);

    public void delShifts(long sid,String list);
}
