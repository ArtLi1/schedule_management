package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "dbAccess",contextId = "preference")
public interface preferenceApi {
    @GetMapping("/preference/uid/{uid}")
    preferences getPreferenceByUid(@PathVariable long uid);

    @GetMapping("/preference/sid/{sid}")
    List<preferences> getPreferenceBySid(@PathVariable long sid);

    @PostMapping("/preference")
    void addPreference(@Valid @RequestBody preferences preferences);

    @PutMapping("/preference")
    boolean modifyPreference(@RequestBody preferences preferences);

    @DeleteMapping("/preference")
    boolean delPreference(@RequestBody long uid);

}
