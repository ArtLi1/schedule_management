package tgu.clwlc.db_access.Service.Impl;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.API.dbAccessApi.preferenceApi;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.db_access.Service.Interface.preferenceService;
import tgu.clwlc.db_access.Utils.MongoUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class preferenceServiceImpl implements preferenceService {

    @Resource
    MongoTemplate mongoTemplate;

    FindAndModifyOptions options = new FindAndModifyOptions().returnNew(false).upsert(false).remove(true);



    @Override
    public boolean modify(preferences preferences) {
        try {
            mongoTemplate.findAndModify(new Query(Criteria.where("uid").is(preferences.getUid())), MongoUtils.update(preferences,"uid"),options, tgu.clwlc.FeignClient.pojo.mongo.preferences.class);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public preferences getPreference(long id) {
        List<preferences> list = mongoTemplate.find(new Query(Criteria.where("uid").is(id)), preferences.class);
        if(list.size()>0) return list.get(0);
        return null;
    }

    @Override
    public void addPreference(preferences preferences) {
        mongoTemplate.insert(preferences);
    }


}
