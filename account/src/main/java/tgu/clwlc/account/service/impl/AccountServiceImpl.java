package tgu.clwlc.account.service.impl;

import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.API.dbAccessApi.userApi;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.FeignClient.util.PassWordEncoder;
import tgu.clwlc.account.service.AccountService;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    PassWordEncoder passWordEncoder;

    @Resource
    userApi userApi;

    public result sign(User user){

        //账户重复查询以及id更改在db模块做完

        user.setPassword(passWordEncoder.encode(user.getPassword()));
        boolean b = userApi.addUser(user);
        if(b) return result.success("注册成功");
        return result.fail("注册失败");
    }

    public result page(int page, int pageSize, String name){
        return null;
    }
}
