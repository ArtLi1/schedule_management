package tgu.clwlc.db_access.Service.Impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.db_access.Service.Interface.preferenceService;
import tgu.clwlc.db_access.Utils.MongoUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class preferenceServiceImpl implements preferenceService {

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    MongoUtils mongoUtils;



    @Override
    public boolean modify(preferences preferences) {
        try {
            Object o = mongoUtils.Modify(preferences,MongoUtils.defaultOptions());
            return o!=null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public preferences getPreferenceByUid(long id) {
        List<preferences> list = mongoTemplate.find(new Query(Criteria.where("uid").is(id)), preferences.class);
        if(list.size()>0) return list.get(0);
        return null;
    }

    @Override
    public List<preferences> getPreferenceBySid(long id) {
        return mongoTemplate.find(new Query(Criteria.where("uid").is(id)), preferences.class);
    }

    @Override
    public void addPreference(preferences preferences) {
        mongoTemplate.insert(preferences);
    }

    @Override
    public boolean delPreference(long uid) {
        try {
            return mongoUtils.remove(new preferences(uid), "uid")>0;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
