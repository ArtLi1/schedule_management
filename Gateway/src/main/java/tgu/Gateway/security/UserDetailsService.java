package tgu.Gateway.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;
import tgu.clwlc.FeignClient.API.dbAccessApi.userApi;

import javax.annotation.Resource;

public class UserDetailsService implements ReactiveUserDetailsService {

    @Resource
    userApi userApi;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return null;
    }
}
