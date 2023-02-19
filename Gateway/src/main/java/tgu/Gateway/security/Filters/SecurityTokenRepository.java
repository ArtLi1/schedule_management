package tgu.Gateway.security.Filters;

import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tgu.Gateway.Utils.JWTGenerate;
import tgu.Gateway.pojo.tokenUser;

import javax.annotation.Resource;

@Component
public class SecurityTokenRepository implements ServerSecurityContextRepository {

    @Resource
    JWTGenerate jwt;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(!StringUtils.hasText(token)) return Mono.empty();
        try {
            tokenUser user = jwt.TokenToObject(tokenUser.class, token);
            if(user==null) return Mono.empty();
            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.permission());
            emptyContext.setAuthentication(auth);
            return Mono.just(emptyContext);
        }catch (Exception e){
            e.printStackTrace();
            return Mono.empty();
        }
    }
}
