package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.User;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "dbAccess",contextId = "user")
public interface userApi {

    @GetMapping("/user/uid/{uid}")
    User getUserByUid(@PathVariable long uid);

    @GetMapping("/user/email/{email}")
    User getUserByEmail(@PathVariable String email);

    @GetMapping("/user/phone/{phone}")
    User getUserByPhone(@PathVariable long phone);

    @GetMapping("/user/sid/{sid}")
    List<User> getUserList(@PathVariable long sid);

    @PostMapping("/user")
    boolean addUser(@Valid @RequestBody User user);

    @PutMapping("/user")
    boolean modifyUser(@RequestBody User user);

    @DeleteMapping("/user")
    boolean removeUser(@RequestBody long uid);
}
