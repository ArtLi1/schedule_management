package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tgu.clwlc.FeignClient.pojo.mysql.User;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "dbAccess",contextId = "user")
public interface userApi {
    @GetMapping("/user/uid/{uid}")
    User getUser(@PathVariable long uid);

    @GetMapping("/user/sid/{sid}")
    List<User> getUserList(@PathVariable long sid);

    @PostMapping("/user")
    int addUser(@Valid @RequestBody User user);
}
