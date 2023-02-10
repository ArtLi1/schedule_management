package tgu.clwlc.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.account.dao.AccountDao;
import tgu.clwlc.account.service.AccountService;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, User> implements AccountService {
}
