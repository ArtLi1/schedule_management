package tgu.Schedule.service.Impl;

import org.springframework.stereotype.Service;
import tgu.Schedule.service.Interface.shiftsService;
import tgu.Schedule.service.shiftsGenerate;
import tgu.clwlc.FeignClient.API.dbAccessApi.shiftsApi;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

import javax.annotation.Resource;

@Service
public class shiftServiceImpl implements shiftsService {


    @Resource
    shiftsGenerate shiftsGenerate;

    @Resource
    shiftsApi shiftsApi;


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
    public result delShifts(long sid, String date) {
        return result.State(shiftsApi.delShifts(sid, date));
    }


}
