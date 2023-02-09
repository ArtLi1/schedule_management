package tgu.clwlc.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.account.common.Result;
import tgu.clwlc.account.service.AccountService;
import tgu.clwlc.account.utils.ValidateCodeUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
    public result login(HttpServletRequest request, @RequestBody User user){
        //1 将页面提交的密码password进行md5加密处理
        String password= user.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        //2 根据页面提交的手机号phone查询数据库
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        User user1 = accountService.getOne(queryWrapper);
        //3 如果没有查询到则返回登陆失败结果
        if(user1==null){
            return result.fail("用户不存在");
        }
        //4 密码比对，如果不一致则返回登陆失败结果
        if(!user1.getPassword().equals(password)){
            return result.fail("密码或用户名错误");
        }
        //5 登陆成功，将用户ID存入Session并返回登陆成功结果
        request.getSession().setAttribute("user",user1.getId());
        return result.success(user1);
    }

    /**
     * 用户退出登陆
     */
    @PostMapping("/logout")
    public result logout(HttpServletRequest request){
        //清理session中保存的当前登陆用户的id
        request.getSession().removeAttribute("user");
        return result.success("退出成功");

    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public result getById(@PathVariable Long id){
        User user = accountService.getById(id);
        if(user!=null){
            return result.success(user);
        }
        return result.fail("没有查询到对应用户信息");
    }

    /**
     * 根据id修改员工信息
     * @param request
     * @param user
     * @return
     */
    @PutMapping
    public result update(HttpServletRequest request,@RequestBody User user){
        log.info(user.toString());
        Long userId=(Long) request.getSession().getAttribute("user");
        accountService.updateById(user);
        return result.success("员工信息修改成功");
    }

    /**
     * 新增用户（员工）
     * @param user
     * @return
     */
    @PostMapping
    public result save(@RequestBody User user){
        log.info("新增用户信息：{}",user.toString());
        //设置初始密码，md5加密处理
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //2 根据页面提交的手机号phone查询数据库
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        User user1 = accountService.getOne(queryWrapper);
        //如果手机号对应用户已存在，则新增失败
        if(user1!=null){
            return result.fail("该手机号已存在用户");
        }else{
            accountService.save(user);
            return result.success("新增用户成功");
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
    public result page(int page, int pageSize, String name){
        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        //添加一个过滤条件
        queryWrapper.like(name!=null,User::getName,name);
        //执行查询
        accountService.page(pageInfo,queryWrapper);
        return result.success(pageInfo);
    }

    /**
     * 发送手机短信验证码
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public result sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        Long phone = user.getPhone();
        if(phone!=null){
            //生成随机的6位验证码
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            log.info("code={}",code);
            //需要将生成的验证码保存到session
            session.setAttribute(phone.toString(),code);
            return result.success("手机验证码短信发送成功");
        }
        return result.fail("短信发送失败");
    }

    /**
     * 比对验证码是否一致,并修改密码
     * @param map
     * @param session
     * @return
     */
    @PutMapping("/updatePwd")
    public result updatePassword(@RequestBody Map map, HttpSession session){
        log.info(map.toString());
        //获取手机号
        Long phone =(Long) map.get("phone");
        //获取验证码
        String code = map.get("code").toString();
        //获取用户提交过来的密码
        String password = map.get("password").toString();
        //密码md5加密
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        //从session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone.toString());
        //比对页面提交到验证码和session中保存的验证码
        if(codeInSession!=null && codeInSession.equals(code)){
            //比对成功
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = accountService.getOne(queryWrapper);
            if(user!=null){
                user.setPassword(password);
                accountService.updateById(user);
                return result.success("密码修改成功");
            }else{
                return result.fail("该手机号用户不存在");
            }

        }else {
            return result.fail("验证失败");
        }

    }




}
