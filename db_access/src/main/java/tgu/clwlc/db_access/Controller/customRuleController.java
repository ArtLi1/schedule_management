package tgu.clwlc.db_access.Controller;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/customRule")
public class customRuleController {

    @Resource
    MongoTemplate mongoTemplate;

    @GetMapping("/{sid}")
    public custom_rules getRules(@PathVariable long sid){
        List<custom_rules> list = mongoTemplate.find(new Query(Criteria.where("sid").is(sid)), custom_rules.class);
        if(list.size()>0) return list.get(0);
        return null;
    }


}
