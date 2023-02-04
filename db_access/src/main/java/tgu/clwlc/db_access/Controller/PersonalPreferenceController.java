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

    @GetMapping("/uid/{uid}")
    public preferences getPreferenceByUid(@PathVariable long uid){
        return preferenceService.getPreferenceByUid(uid);
    }

    @GetMapping("/sid/{sid}")
    public List<preferences> getPreferenceBySid(@PathVariable long sid){
        return preferenceService.getPreferenceBySid(sid);
    }

    @PostMapping
    public void addPreference(@Valid @RequestBody preferences preferences){
        preferenceService.addPreference(preferences);
    }

    @PutMapping
    public boolean modifyPreference(@RequestBody preferences preferences){
        return preferenceService.modify(preferences);
    }

    @DeleteMapping
    public boolean delPreference(@RequestBody long uid){
        return preferenceService.delPreference(uid);
    }

}
