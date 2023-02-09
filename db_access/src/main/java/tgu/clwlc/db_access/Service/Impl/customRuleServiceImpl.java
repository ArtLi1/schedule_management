package tgu.clwlc.db_access.Service.Impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;
import tgu.clwlc.db_access.Service.Interface.customRuleService;
import tgu.clwlc.db_access.Utils.MongoUtils;

import javax.annotation.Resource;
import java.util.List;
@Service
public class customRuleServiceImpl implements customRuleService {

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    MongoUtils mongoUtils;

    @Override
    public custom_rules getRules(long sid) {
        List<custom_rules> list = mongoTemplate.find(new Query(Criteria.where("sid").is(sid)), custom_rules.class);
        if(list.size()>0) return list.get(0);
        return null;
    }

    @Override
    public boolean modifyRules(custom_rules custom_rules) {
        try {
            Object sid = mongoUtils.Modify(custom_rules, MongoUtils.defaultOptions());
            return sid != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addRules(custom_rules rule) {
        mongoTemplate.insert(rule);
    }

    @Override
    public boolean delRules(long sid) {
        custom_rules custom_rules = new custom_rules();
        custom_rules.setSid(sid);
        try {
            return mongoUtils.remove(custom_rules)>0;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
