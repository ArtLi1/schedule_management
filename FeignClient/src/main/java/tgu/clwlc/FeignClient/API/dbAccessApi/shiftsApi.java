package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.secure.secureShifts;

@FeignClient(name = "dbAccess",contextId = "shifts")
public interface shiftsApi {
    @PutMapping("/shifts")
    void addShifts(@RequestBody secureShifts shifts);

    @DeleteMapping("/shifts")
    boolean delShifts(@RequestHeader long id, @RequestHeader String date);

    @PostMapping("/shifts")
    void modifyShifts(@RequestBody secureShifts shifts);

    @GetMapping("/shifts/{sid}/{date}")
    secureShifts getShifts(@PathVariable long sid, @PathVariable String date);
}
