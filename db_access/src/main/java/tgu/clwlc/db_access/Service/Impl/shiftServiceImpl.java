package tgu.clwlc.db_access.Service.Impl;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;
import tgu.clwlc.db_access.Service.Interface.shiftsService;
import tgu.clwlc.db_access.Utils.MongoUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static tgu.clwlc.FeignClient.util.StringContent.PROJECT_NAME;
import static tgu.clwlc.FeignClient.util.StringContent.SHIFTS_NAME;


@Service
public class shiftServiceImpl implements shiftsService {

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    MongoUtils mongoUtils;

    @Resource
    StringRedisTemplate stringRedisTemplate;


    String CacheName = PROJECT_NAME+SHIFTS_NAME;

    @Override
    public secureShifts getShifts(long sid, String date) {
        ValueOperations<String, String> stringVOps = stringRedisTemplate.opsForValue();
        secureShifts shifts = JSON.parseObject(stringVOps.get(joint(sid, date)), secureShifts.class);
        if (shifts == null) {
            List<secureShifts> shiftsList = mongoTemplate.find(new Query(Criteria.where("sid").is(sid).and("date").is(date)),secureShifts.class);
            if(shiftsList.size()<1){
               return null;
            }
            shifts = shiftsList.get(0);
            stringVOps.set(joint(sid, date),JSON.toJSONString(shifts),8, TimeUnit.HOURS);
        }
        return shifts;
    }

    @Override
    public void addShifts(secureShifts shifts) {
            mongoTemplate.insert(shifts);
    }

    @Override
    public boolean delShifts(long sid, String date) {
        stringRedisTemplate.delete(joint(sid, date));
        try {
            return mongoUtils.remove(new secureShifts(sid,date), "sid", "date")>0;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean modifyShifts(secureShifts shifts) {
        try {
           Object o = mongoUtils.Modify(shifts,MongoUtils.defaultOptions());
            return o!=null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private String joint(long sid,String date){
        return CacheName+":"+sid+":"+date;
    }
}
