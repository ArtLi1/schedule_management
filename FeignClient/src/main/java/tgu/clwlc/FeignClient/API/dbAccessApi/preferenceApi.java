package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tgu.clwlc.FeignClient.pojo.mongo.preferences;

import javax.validation.Valid;

@FeignClient(name = "dbAccess",contextId = "preference")
public interface preferenceApi {
    @GetMapping("/preference/{id}")
    preferences getPreference(@PathVariable long id);

    @PostMapping("/preference")
    int addPreference(@Valid @RequestBody preferences preferences);
}
