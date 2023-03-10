package tgu.clwlc.db_access.Controller;


import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.db_access.Service.Interface.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping("/phone/{phone}")
    public User getUserByPhone(@PathVariable long phone){
        return userService.getUserByPhone(phone);
    }


    @GetMapping("/uid/{uid}")
    public User getUserByUid(@PathVariable long uid){
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
