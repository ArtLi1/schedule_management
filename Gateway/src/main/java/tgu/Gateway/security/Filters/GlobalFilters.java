package tgu.Gateway.security.Filters;

import com.alibaba.fastjson2.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import tgu.Gateway.Utils.JWTGenerate;
import tgu.clwlc.FeignClient.pojo.result;
import javax.annotation.Resource;
@Component
public class GlobalFilters implements WebFilter {

    @Resource
    JWTGenerate jwtGenerate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getPath().value();
        if (path.contains("/auth/login") || path.contains("/auth/signout")) return chain.filter(exchange);

        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null) {
            return this.writeErrorMessage(response, HttpStatus.NOT_ACCEPTABLE, "没有携带token");
        }
        String token = jwtGenerate.getTokenString(auth);
        try {
            exchange.getAttributes().put("token", token);
        } catch (Exception e) {
            return this.writeErrorMessage(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return chain.filter(exchange);
    }

    private Mono<Void> writeErrorMessage(ServerHttpResponse response, HttpStatus notAcceptable, String token) {
        response.setStatusCode(notAcceptable);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        result result = tgu.clwlc.FeignClient.pojo.result.fail(token);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONBytes(result));
        return response.writeWith(Mono.just(dataBuffer));
    }



}
