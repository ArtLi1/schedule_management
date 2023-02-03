package tgu.Schedule.service.Impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tgu.Schedule.service.Interface.shiftsService;
import tgu.Schedule.service.shiftsGenerate;
import tgu.clwlc.FeignClient.API.dbAccessApi.shiftsApi;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

import javax.annotation.Resource;
import java.util.List;

import static tgu.clwlc.FeignClient.util.StringContent.PROJECT_NAME;
import static tgu.clwlc.FeignClient.util.StringContent.SHIFTS_NAME;


@Service
public class shiftServiceImpl implements shiftsService {


    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    shiftsGenerate shiftsGenerate;

    @Resource
    shiftsApi shiftsApi;

    String CacheName = PROJECT_NAME+SHIFTS_NAME;

    @Override
    public result getShifts(String date, long sid) {
        secureShifts shifts = shiftsApi.getShifts(sid, date);
        if(shifts==null){
            shiftsGenerate.generate(date,sid);
            return result.fail("正在生成中");
        }
        return result.success(shifts);
    }

    @Override
    public void addShifts(List<secureShifts> shifts) {

        for (secureShifts shift : shifts) {
            shiftsApi.addShifts(shifts);
        }
    }

    @Override
    public void delShifts(long sid,String list) {
        shiftsApi.delShifts(sid,list);
    }


}
