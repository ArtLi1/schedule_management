package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;

@FeignClient(name = "dbAccess",contextId = "customRule")
public interface customRuleApi {
    @GetMapping("/customRule/{sid}")
    custom_rules getRules(@PathVariable long sid);

    @PutMapping("/customRule")
    boolean modifyRules(@RequestBody custom_rules custom_rules);

    @PostMapping("/customRule")
    void addRules(@RequestBody custom_rules rule);

    @DeleteMapping("/customRule")
    boolean delRules(@RequestBody long sid);

}
