package tgu.Gateway.security.Filters;

import com.alibaba.fastjson2.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tgu.Gateway.Utils.JWTGenerate;
import tgu.Gateway.pojo.tokenUser;
import tgu.clwlc.FeignClient.pojo.result;

import javax.annotation.Resource;
import java.time.Duration;

@Component
public class AuthenticationSuccess extends WebFilterChainServerAuthenticationSuccessHandler {

    private final long day = 30;

    private final long Expire = day*24*60*60*1000;

    @Resource
    private JWTGenerate jwt;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add("Content-Type","application/json; charset=UTF-8");
        headers.add("Cache-Control","no-store, no-cache, must-revalidate, max-age=0");

        tokenUser user = new tokenUser((String) authentication.getPrincipal(),"");
        authentication.getAuthorities().forEach(s->user.setPermission(s.getAuthority()));
        String token = jwt.getToken(user);
        response.addCookie(ResponseCookie.from("token",token).maxAge(Duration.ofDays(day)).path("/").build());

        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONBytes(result.success("登录成功")));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
