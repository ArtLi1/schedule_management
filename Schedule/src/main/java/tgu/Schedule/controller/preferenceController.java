package tgu.Schedule.controller;


import org.springframework.web.bind.annotation.*;
import tgu.Schedule.service.Interface.preferenceService;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.result;

import javax.annotation.Resource;

@RestController
@RequestMapping("/preference")
public class preferenceController {

    @Resource
    preferenceService preferenceService;

    @PostMapping
    public result addPreference(@RequestBody preferences preferences){
        return preferenceService.addPreference(preferences);
    }

    @GetMapping("/uid/{uid}")
    public result getPreferenceByUid(@PathVariable long uid){
        return preferenceService.getPreferenceByUid(uid);
    }

    @GetMapping("/sid/{sid}")
    public result getPreferenceBySid(@PathVariable long sid){
        return preferenceService.getPreferenceByUid(sid);
    }

    @PutMapping
    public result modifyPreference(@RequestBody preferences preferences){
        return preferenceService.modifyPreference(preferences);
    }

    @DeleteMapping
    public result delPreference(@RequestBody long uid){
        return preferenceService.delPreference(uid);
    }
}
