package tgu.Schedule.service.Impl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import tgu.Schedule.service.shiftsGenerate;
import tgu.Schedule.service.Interface.shiftsService;
import tgu.clwlc.FeignClient.API.dbAccessApi.shiftsApi;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;
import tgu.clwlc.FeignClient.util.DateUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class shiftsServiceImpl implements shiftsService {

    @Resource
    shiftsApi shiftsApi;

    @Resource
    shiftsGenerate generate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public result getShifts(String date, long sid) {

        ValueOperations<String, String> ValueOps = stringRedisTemplate.opsForValue();

//        ValueOps.get()

        List<secureShifts> list = new ArrayList<>();
        for(int i=0;i<7;i++){
            secureShifts shifts = shiftsApi.getShifts(sid, date);
            if(shifts==null){
                generate.generate(date,sid);
                return result.fail("排班生成中");
            }
            list.add(shifts);
        }
        return result.success(list);
    }



//    public String
}
