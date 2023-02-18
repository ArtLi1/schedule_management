package tgu.Gateway.security.Token;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import tgu.Gateway.Utils.JWTGenerate;
import tgu.Gateway.pojo.tokenUser;

import javax.annotation.Resource;

@Component
public class AuthenticationManger implements ReactiveAuthenticationManager {

    @Resource
    private JWTGenerate jwtGenerate;


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtGenerate.TokenToObject(tokenUser.class, jwtGenerate.getTokenString((String)auth.getPrincipal())))
                .onErrorResume(e-> Mono.empty())
                .map(user-> {
                    return new UsernamePasswordAuthenticationToken(user.getUsername(),null,user.permissionList());
                });
    }



}
