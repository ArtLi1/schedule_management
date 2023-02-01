package tgu.clwlc.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.account.common.Result;
import tgu.clwlc.account.entity.User;
import tgu.clwlc.account.service.AccountService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    public Result<User> login(HttpServletRequest request, @RequestBody User user){
        //1 将页面提交的密码password进行md5加密处理
        String password= user.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        //2 根据页面提交的手机号phone查询数据库
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        User user1 = accountService.getOne(queryWrapper);
        //3 如果没有查询到则返回登陆失败结果
        if(user1==null){
            return Result.error("用户不存在");
        }
        //4 密码比对，如果不一致则返回登陆失败结果
        if(!user1.getPassword().equals(password)){
            return Result.error("密码或用户名错误");
        }
        //5 登陆成功，将用户ID存入Session并返回登陆成功结果
        request.getSession().setAttribute("user",user1.getId());
        return Result.success(user1);
    }

    /**
     * 用户退出登陆
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        //清理session中保存的当前登陆用户的id
        request.getSession().removeAttribute("user");
        return Result.success("退出成功");

    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id){
        User user = accountService.getById(id);
        if(user!=null){
            return Result.success(user);
        }
        return Result.error("没有查询到对应用户信息");
    }

    /**
     * 根据id修改员工信息
     * @param request
     * @param user
     * @return
     */
    @PutMapping
    public Result<String> update(HttpServletRequest request,@RequestBody User user){
        log.info(user.toString());
        Long userId=(Long) request.getSession().getAttribute("user");
        accountService.updateById(user);
        return Result.success("员工信息修改成功");
    }


}
