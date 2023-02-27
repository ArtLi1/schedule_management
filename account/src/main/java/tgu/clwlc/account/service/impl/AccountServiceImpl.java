package tgu.clwlc.account.service.impl;

import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.result;
import tgu.clwlc.FeignClient.util.PassWordEncoder;
import tgu.clwlc.account.service.AccountService;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    PassWordEncoder passWordEncoder;

    public result sign(User user){
        user.setPassword(passWordEncoder.encode(user.getPassword()));

    }

    public result page(int page, int pageSize, String name){
        return null;
    }
}
