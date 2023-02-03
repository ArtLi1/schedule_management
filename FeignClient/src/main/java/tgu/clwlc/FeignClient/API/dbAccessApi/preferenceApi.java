package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;

import javax.validation.Valid;

@FeignClient(name = "dbAccess",contextId = "preference")
public interface preferenceApi {
    @GetMapping("/preference/{id}")
    preferences getPreference(@PathVariable long id);

    @PostMapping("/preference")
    void addPreference(@Valid @RequestBody preferences preferences);

    @PutMapping("/preference")
    public boolean modifyPreference(@RequestBody preferences preferences);

}
