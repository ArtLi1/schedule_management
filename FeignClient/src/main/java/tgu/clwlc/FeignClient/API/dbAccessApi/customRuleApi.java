package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;

@FeignClient(name = "dbAccess",contextId = "customRule")
public interface customRuleApi {
    @GetMapping("/customRule/{sid}")
    custom_rules getRules(@PathVariable long sid);
}
