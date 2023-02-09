package tgu.Schedule.service.Interface;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;
import tgu.clwlc.FeignClient.pojo.result;

public interface customRuleService {
    result getRule(long sid);

    result modifyRule(custom_rules rules);

    result addRule(custom_rules rules);
}
