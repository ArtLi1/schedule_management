package tgu.clwlc.db_access.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.UserWithP;
import tgu.clwlc.db_access.dao.UserWithPMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userP")
public class UserWithPController {
    @Resource
    UserWithPMapper userPMapper;

    @GetMapping("/uid/{uid}")
    public UserWithP getUserByUid(@PathVariable Long uid){
        return userPMapper.selectByUid(uid);
    }

    @GetMapping("/email/{email}")
    public UserWithP getUserByEmail(@PathVariable String email){
        return userPMapper.selectByEmail(email);
    }

    @GetMapping("/phone/{phone}")
    public UserWithP getUserByPhone(@PathVariable String phone){
        return userPMapper.selectByPhone(phone);
    }
}
