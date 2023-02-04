package tgu.Schedule.service.Impl;

import org.springframework.stereotype.Service;
import tgu.Schedule.service.Interface.customRuleService;
import tgu.clwlc.FeignClient.API.dbAccessApi.customRuleApi;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;
import tgu.clwlc.FeignClient.pojo.result;

import javax.annotation.Resource;

@Service
public class customRuleServiceImpl implements customRuleService {

    @Resource
    customRuleApi customRuleApi;

    @Override
    public result getRule(long sid) {
        custom_rules rules = customRuleApi.getRules(sid);
        if(rules==null) return result.success(new custom_rules());
        return result.success(rules);
    }

    @Override
    public result modifyRule(custom_rules rules) {
        boolean b = customRuleApi.modifyRules(rules);
        if(!b) return result.fail("修改失败");
        return result.success("");
    }

    @Override
    public result addRule(custom_rules rules) {
        customRuleApi.addRules(rules);
        custom_rules rule = customRuleApi.getRules(rules.getSid());
        return result.State(rule.equals(rules));
    }
}
