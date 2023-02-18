package tgu.Gateway.security.conf;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tgu.Gateway.pojo.SecurityUser;
import tgu.clwlc.FeignClient.API.dbAccessApi.userApi;
import tgu.clwlc.FeignClient.pojo.mysql.User;

import javax.annotation.Resource;

@Component
public class UserDetailsService implements ReactiveUserDetailsService {

    @Resource
    userApi userApi;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        User user = userApi.getUser(username);
        if(user==null){
            return Mono.empty();
        }
        return Mono.just(new SecurityUser(user));
    }
}
