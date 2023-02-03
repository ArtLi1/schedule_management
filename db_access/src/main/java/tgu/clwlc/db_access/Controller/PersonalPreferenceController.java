package tgu.clwlc.db_access.Controller;


import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.db_access.Service.Interface.preferenceService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RequestMapping("/preference")
@RestController
public class PersonalPreferenceController {

    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    preferenceService preferenceService;

    @GetMapping("/{id}")
    public preferences getPreference(@PathVariable long id){
        return preferenceService.getPreference(id);
    }

    @PostMapping
    public void addPreference(@Valid @RequestBody preferences preferences){
        preferenceService.addPreference(preferences);
    }

    @PutMapping
    public boolean modifyPreference(@RequestBody preferences preferences){
        return preferenceService.modify(preferences);
    }

}
