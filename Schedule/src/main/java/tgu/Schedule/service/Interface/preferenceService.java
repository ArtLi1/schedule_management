package tgu.Schedule.service.Interface;

import org.springframework.web.bind.annotation.RequestBody;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;
import tgu.clwlc.FeignClient.pojo.result;

public interface preferenceService {

    public result addPreference(preferences preferences);

    public result getPreference(long uid);

    public result modifyPreference(preferences preferences);

}
