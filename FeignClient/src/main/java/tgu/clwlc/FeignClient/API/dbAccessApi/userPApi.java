package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tgu.clwlc.FeignClient.pojo.mysql.UserWithP;

@FeignClient(name = "dbAccess",contextId = "UserP")
public interface userPApi {
    @GetMapping("/userP/uid/{uid}")
    UserWithP getUserByUid(@PathVariable Long uid);
    @GetMapping("/userP/email/{email}")
    UserWithP getUserByEmail(@PathVariable String email);

    @GetMapping("/userP/phone/{phone}")
    public UserWithP getUserByPhone(@PathVariable String phone);
}
