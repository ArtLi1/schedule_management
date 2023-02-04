package tgu.clwlc.db_access.Service.Interface;

import org.springframework.web.bind.annotation.RequestBody;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;

import java.util.List;

public interface preferenceService {
    public boolean modify(preferences preferences);

    public preferences getPreferenceByUid(long id);

    public List<preferences> getPreferenceBySid(long id);

    public void addPreference(preferences preferences);

    public boolean delPreference(long uid);

}
