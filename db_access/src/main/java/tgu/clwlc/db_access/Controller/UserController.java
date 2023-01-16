package tgu.clwlc.db_access.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.util.SnowflakeIdGenerate;
import tgu.clwlc.db_access.dao.UserMapper;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    SnowflakeIdGenerate idGenerate = new SnowflakeIdGenerate(2);

    @GetMapping("/uid/{uid}")
    public User getUser(@PathVariable long uid){
        return userMapper.selectById(uid);
    }

    @GetMapping("/sid/{sid}")
    public List<User> getUserList(@PathVariable long sid){
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("sid",sid);
        return userMapper.selectList(query);
    }

    @PostMapping
    public int addUser(@Valid @RequestBody User user){
        user.setId(idGenerate.nextId());
        userMapper.insert(user);
        return 0;
    }
}
