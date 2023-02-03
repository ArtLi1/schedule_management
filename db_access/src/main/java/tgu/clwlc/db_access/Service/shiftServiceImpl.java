package tgu.clwlc.db_access.Service;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;
import tgu.clwlc.FeignClient.util.DateUtils;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static tgu.clwlc.FeignClient.util.StringContent.PROJECT_NAME;
import static tgu.clwlc.FeignClient.util.StringContent.SHIFTS_NAME;


@Service
public class shiftServiceImpl implements shiftsService{

    @Resource
    MongoTemplate mongoTemplate;

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
    public void addShifts(List<secureShifts> shifts) {
        for (secureShifts shift : shifts) {
            mongoTemplate.insert(shift);
        }
    }

    @Override
    public void delShifts(long sid,String date) {
            stringRedisTemplate.delete(joint(sid,date));
            mongoTemplate.remove(new Query(Criteria.where("date").is(date).and("sid").is(sid)), secureShifts.class);
    }


    private String joint(long sid,String date){
        return CacheName+":"+sid+":"+date;
    }
}
