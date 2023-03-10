package tgu.Gateway.security.conf;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tgu.Gateway.pojo.SecurityUser;
import tgu.clwlc.FeignClient.API.dbAccessApi.userPApi;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.UserWithP;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Resource
    userPApi userPApi;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Future<UserWithP> submit = null;
        if (username.contains("@")) {
            submit = executorService.submit(() -> userPApi.getUserByEmail(username));
        } else {
            submit = executorService.submit(() -> userPApi.getUserByPhone(username));
        }
        try {
            UserWithP user = submit.get();
            return new SecurityUser(user);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
