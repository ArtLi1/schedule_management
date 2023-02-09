package tgu.Schedule.controller;

import org.springframework.web.bind.annotation.*;
import tgu.Schedule.service.Interface.customRuleService;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;
import tgu.clwlc.FeignClient.pojo.result;

import javax.annotation.Resource;

@RestController
@RequestMapping("/customRule")
public class customRuleController {

    @Resource
    customRuleService customRuleService;

    @GetMapping("/{sid}")
    public result getRule(@PathVariable long sid){
        return customRuleService.getRule(sid);
    }

    @PostMapping
    public result modifyRule(@RequestBody custom_rules rules){
        return customRuleService.modifyRule(rules);
    }

    @PutMapping
    public result addRule(@RequestBody custom_rules rules){
        return customRuleService.addRule(rules);
    }

}
