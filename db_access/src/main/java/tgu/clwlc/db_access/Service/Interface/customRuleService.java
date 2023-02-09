package tgu.clwlc.db_access.Service.Interface;

import tgu.clwlc.FeignClient.pojo.mongo.custom_rules;

public interface customRuleService {

    public custom_rules getRules(long sid);

    public boolean modifyRules(custom_rules custom_rules);

    public void addRules(custom_rules rule);

    public boolean delRules(long sid);
}
