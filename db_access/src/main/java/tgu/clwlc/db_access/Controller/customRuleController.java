package tgu.clwlc.db_access.Controller;

import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;
import tgu.clwlc.db_access.Service.Interface.customRuleService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/customRule")
public class customRuleController {

    @Resource
    customRuleService customRuleService;

    @GetMapping("/{sid}")
    public custom_rules getRules(@PathVariable long sid){
        return customRuleService.getRules(sid);
    }

    @PutMapping
    public boolean modifyRules(@RequestBody custom_rules custom_rules){
        return customRuleService.modifyRules(custom_rules);
    }

    @PostMapping
    public void addRules(@RequestBody custom_rules rule){
        customRuleService.addRules(rule);
    }

    @DeleteMapping
    public boolean delRules(@RequestBody long sid){
        return customRuleService.delRules(sid);
    }



}
