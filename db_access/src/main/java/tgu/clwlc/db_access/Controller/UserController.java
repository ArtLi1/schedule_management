package tgu.clwlc.db_access.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.util.SnowflakeIdGenerate;
import tgu.clwlc.db_access.Service.Interface.UserService;
import tgu.clwlc.db_access.dao.UserMapper;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/username/{username}")
    public User getUser(@PathVariable String username){
        return userService.getUser(username);
    }

    @GetMapping("/uid/{uid}")
    public User getUser(@PathVariable long uid){
        return userService.getUser(uid);
    }

    @GetMapping("/sid/{sid}")
    public List<User> getUserList(@PathVariable long sid){
        return userService.getUserList(sid);
    }

    @PostMapping
    public boolean addUser(@Valid @RequestBody User user){
       return userService.addUser(user);
    }

    @PutMapping
    public boolean modifyUser(@RequestBody User user){
        return userService.modifyUser(user);
    }

    @DeleteMapping
    public boolean removeUser(@RequestBody long uid){
        return userService.removeUser(uid);
    }
}
