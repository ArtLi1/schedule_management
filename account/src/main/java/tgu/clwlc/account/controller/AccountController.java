package tgu.clwlc.account.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.account.service.AccountService;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    /**
     * 新增用户（员工）
     * @param user
     * @return
     */
    @PostMapping
    public result sign(@RequestBody User user){
        return accountService.sign(user);
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
        return accountService.page(page,pageSize,name);
    }
}
