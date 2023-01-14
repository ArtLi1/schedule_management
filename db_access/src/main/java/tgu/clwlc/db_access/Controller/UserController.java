package tgu.clwlc.db_access.Controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.db_access.dao.UserMapper;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping
    public int addUser(@Valid @RequestBody User user){
        userMapper.insert(user);
        return 0;
    }
}
