package tgu.clwlc.db_access.Service.Interface;

import tgu.clwlc.FeignClient.pojo.mongo.preferences;

public interface preferenceService {
    public boolean modify(preferences preferences);

    public preferences getPreference(long id);

    public void addPreference(preferences preferences);

}
