package tgu.clwlc.account.service;

import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.pojo.result;

public interface AccountService  {
    result sign(User user);

    result page(int page, int pageSize, String name);
}
