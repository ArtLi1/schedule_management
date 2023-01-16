package tgu.clwlc.db_access.Controller;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/preference")
@RestController
public class PersonalPreferenceController {

    @Resource
    MongoTemplate mongoTemplate;


    @GetMapping("/{id}")
    public preferences getPreference(@PathVariable long id){
        List<preferences> list = mongoTemplate.find(new Query(Criteria.where("uid").is(id)), preferences.class);
        if(list.size()>0) return list.get(0);
        return null;
    }

    @PostMapping
    public int addPreference(@Valid @RequestBody preferences preferences){
        mongoTemplate.insert(preferences);
        return 0;
    }

}
