package tgu.Schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tgu.clwlc.FeignClient.API.dbAccessApi;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;
import tgu.clwlc.FeignClient.util.DateUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class shiftsServiceImpl implements shiftsService{

    @Resource
    dbAccessApi dbAccessApi;

    @Resource
    shiftsGenerate generate;

    @Override
    public result getShifts(String date, long sid) {
        List<secureShifts> list = new ArrayList<>();
        Date day = DateUtils.getFirstDayOfWeek(date);
        for(int i=0;i<7;i++){
            secureShifts shifts = dbAccessApi.getShifts(sid, date);
            if(shifts==null){
                generate.generate(date,sid);
                return result.fail("排班生成中");
            }
            list.add(shifts);
        }
        return result.success(list);
    }
}
