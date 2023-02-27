package tgu.Gateway.security.Handler;

import com.alibaba.fastjson2.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tgu.clwlc.FeignClient.pojo.result;

@Component
public class LogoutSuccessHandler implements ServerLogoutSuccessHandler {
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        ServerHttpResponse response = exchange.getExchange().getResponse();
        response.addCookie(ResponseCookie.from("token","").maxAge(0).path("/").build());

        HttpHeaders headers = response.getHeaders();
        headers.add("Content-Type","application/json; charset=UTF-8");
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONBytes(result.fail("已退出登录")));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
