package tgu.Gateway.security.conf;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tgu.Gateway.pojo.SecurityUser;
import tgu.clwlc.FeignClient.API.dbAccessApi.userApi;
import tgu.clwlc.FeignClient.pojo.mysql.User;

import javax.annotation.Resource;
import java.util.concurrent.*;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Resource
    userApi userApi;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Future<User> submit = executorService.submit(() -> userApi.getUser(username));
        try {
            User user = submit.get();
            return new SecurityUser(user);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
