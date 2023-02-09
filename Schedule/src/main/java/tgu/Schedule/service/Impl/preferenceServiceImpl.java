package tgu.Schedule.service.Impl;

import org.springframework.stereotype.Service;
import tgu.Schedule.service.Interface.preferenceService;
import tgu.clwlc.FeignClient.API.dbAccessApi.preferenceApi;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.result;

import javax.annotation.Resource;
import java.util.List;

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
    public result getPreferenceByUid(long uid) {
        preferences preference = preferenceApi.getPreferenceByUid(uid);
        if(preference==null){
            return result.fail("偏好不存在");
        }
        return result.success(preference);
    }

    @Override
    public result getPreferenceBySid(long sid) {
        List<preferences> preferenceBySid = preferenceApi.getPreferenceBySid(sid);
        if(preferenceBySid==null) return result.fail("查询失败");
        return result.success(preferenceBySid);
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

    @Override
    public result delPreference(long uid) {
        boolean b = preferenceApi.delPreference(uid);
        return result.State(b);
    }
}
