package tgu.Schedule.controller;


import org.springframework.web.bind.annotation.*;
import tgu.Schedule.service.Interface.preferenceService;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.result;

import javax.annotation.Resource;

@RequestMapping("/preference")
public class preferenceController {

    @Resource
    preferenceService preferenceService;

    @PostMapping
    public result addPreference(@RequestBody preferences preferences){
        return preferenceService.addPreference(preferences);
    }

    @GetMapping("/{uid}")
    public result getPreference(@PathVariable long uid){
        return preferenceService.getPreference(uid);
    }

    @PutMapping
    public result modifyPreference(@RequestBody preferences preferences){
        return preferenceService.modifyPreference(preferences);
    }
}
