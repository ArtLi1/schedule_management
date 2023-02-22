package tgu.clwlc.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.account.dao.AccountDao;
import tgu.clwlc.account.service.AccountService;
import tgu.clwlc.account.utils.ValidateCodeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/account")
@Api(tags = "账号模块相关接口")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountDao accountDao;

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    @ApiOperation(value = "登陆接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="user",value = "封装有phone, password的json数据",required = true)
    })
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
    @ApiOperation(value = "退出登陆接口")
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
    @ApiOperation(value = "根据id查询用户信息接口，用户修改账号信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "用户id",required = true)
    })
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
    @ApiOperation(value = "修改账号信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="user",value = "封装有user实体类的json数据",required = true)
    })
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
    @ApiOperation(value = "新增用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="user",value = "封装有user实体类的json数据。id和password不需要，后端自动生成",required = true)
    })
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
    @ApiOperation(value = "用户信息分页查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required = true),
            @ApiImplicitParam(name="pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name="name",value = "姓名",required = false),
    })
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
    @ApiOperation(value = "发送手机短信验证码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="user",value = "只需封装有phone的json数据即可",required = true)
    })
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
    @ApiOperation(value = "比对验证码并修改密码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="map",value = "只需封装有phone，code,password的json数据即可",required = true)
    })
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

    /**
     * 查询所有用户信息
     * @return
     */
    @GetMapping
    @ApiOperation(value = "查询所有用户信息接口")
    public result getAll(){
        List<User> users = accountService.list();
        return result.success(users);
    }

    /**
     * 查询所有员工信息
     */
    @GetMapping("/employee")
    @ApiOperation(value = "查询所有员工信息接口")
    public result getAllEmployee(){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPermission,1);
        List<User> employees = accountService.list(queryWrapper);
        return result.success(employees);
    }

    /**
     * 查询所有员工及所有门店管理员信息
     */
    @GetMapping("/empAndAdm")
    @ApiOperation(value = "查询所有员工及所有门店管理员信息接口")
    public result getEmpAndAdm(){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPermission,1).or().eq(User::getPermission,2);
        List<User> list = accountService.list(queryWrapper);
        return result.success(list);
    }

    /**
     * 根据一组id查询一组用户信息
     * @return
     */
    @GetMapping("/getUsers")
    @ApiImplicitParams({
            @ApiImplicitParam(name="ids",value = "用户ID数组，格式：getUsers?id=10001,10002,10003",required = true)
    })
    @ApiOperation(value = "根据一组id查询一组用户信息接口")
    public result getUsers(@RequestParam List<Long> ids){
        List<User> users = accountDao.selectBatchIds(ids);
        return result.success(users);
    }




}
