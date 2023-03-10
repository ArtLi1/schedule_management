package tgu.clwlc.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.account.service.AccountService;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping
public class AccountController {

    @Resource
    private AccountService accountService;

    /**
     * 新增用户（员工）
     * @param user
     * @return
     */
    @PostMapping("/sign")
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
