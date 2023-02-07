package tgu.clwlc.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.account.common.Result;
import tgu.clwlc.account.entity.User;
import tgu.clwlc.account.service.AccountService;



import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    private String code="";
    private Random r=new Random();
    private String phoneNumber;
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

    /**
     * 新增用户（员工）
     * @param user
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody User user){
        //设置初始密码，md5加密处理
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //2 根据页面提交的手机号phone查询数据库
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        User user1 = accountService.getOne(queryWrapper);
        //如果手机号对应用户已存在，则新增失败
        if(user1!=null){
            return Result.error("该手机号已存在用户");
        }else{
            accountService.save(user);
            return Result.success("新增用户成功");
        }
    }

    /**
     * 用户信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name){
        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        //添加一个过滤条件
        queryWrapper.like(name!=null,User::getName,name);
        //执行查询
        accountService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    /**
     * 获取验证码
     * @return
     */
    @GetMapping("/code")
    public Result<String> identifiedCode(String phone){
        code="";
        phoneNumber=phone;
        //生成验证码
        for (int i = 0; i < 6; i++) {
            int num= r.nextInt(10);
            code+=num;
        }
        log.info(code);
        return Result.success("发送验证码成功");
    }

    /**
     * 比对发来的验证码是否一致
     * @param
     * @return
     */
    @PostMapping("/compare")
    public Result<String> compareCode(String inputCode){
        log.info(inputCode);
        if(code.equals(inputCode)){
            return Result.success("验证成功");
        }else{
            return Result.error("验证码输入错误");
        }
    }

    /**
     * 修改密码
     * @param password
     * @return
     */
    @PutMapping("/password")
    public Result<String> changePassword(String password){
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phoneNumber);
        User user = accountService.getOne(queryWrapper);
        user.setPassword(password);
        accountService.updateById(user);
        return Result.success("修改密码成功");
    }


}
