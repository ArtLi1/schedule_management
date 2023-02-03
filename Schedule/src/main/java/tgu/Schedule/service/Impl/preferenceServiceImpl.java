package tgu.Schedule.service.Impl;

import org.springframework.stereotype.Service;
import tgu.Schedule.service.Interface.preferenceService;
import tgu.clwlc.FeignClient.API.dbAccessApi.preferenceApi;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.result;

import javax.annotation.Resource;

@Service
public class preferenceServiceImpl implements preferenceService {

    @Resource
    preferenceApi preferenceApi;

    @Override
    public result addPreference(preferences preferences) {
        preferenceApi.addPreference(preferences);
        return result.success("null");
    }

    @Override
    public result getPreference(long uid) {
        preferences preference = preferenceApi.getPreference(uid);
        if(preference==null){
            return result.fail("偏好不存在");
        }
        return result.success(preference);
    }

    @Override
    public result modifyPreference(preferences preferences) {
        boolean b = preferenceApi.modifyPreference(preferences);
        if(b){
            return result.success(null);
        }else {
            return result.fail("更新失败");
        }
    }
}
