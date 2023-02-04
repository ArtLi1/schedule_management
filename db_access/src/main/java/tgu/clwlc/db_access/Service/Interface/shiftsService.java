package tgu.clwlc.db_access.Service.Interface;

import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

import java.util.List;

@Service
public interface shiftsService {

    public secureShifts getShifts(long sid, String date);

    public void addShifts(List<secureShifts> shifts);

    public boolean delShifts(long sid, String list);

    boolean modifyShifts(secureShifts shifts);
}
