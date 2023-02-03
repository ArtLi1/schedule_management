package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

import java.util.List;

@FeignClient(name = "dbAccess",contextId = "shifts")
public interface shiftsApi {
    @PutMapping("/shifts")
    void addShifts(@RequestBody List<secureShifts> shifts);

    @DeleteMapping("/shifts")
    void delShifts(@RequestParam long id, @RequestBody List<String> list);

    @GetMapping("/shifts/{sid}/{date}")
    secureShifts getShifts(@PathVariable long sid, @PathVariable String date);
}
