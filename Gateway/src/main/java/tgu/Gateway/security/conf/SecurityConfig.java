package tgu.Gateway.security.conf;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import tgu.Gateway.security.Handler.*;
import tgu.Gateway.security.Filters.CookieToHeadersFilter;
import tgu.Gateway.security.Filters.SecurityTokenRepository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class SecurityConfig {

    private AuthenticationEntrypoint authenticationEntrypoint;

    private AuthenticationSuccess authenticationSuccess;

    private AuthenticationFailed authenticationFailed;

    private AccessDeniedHandler accessDeniedHandler;

    private SecurityTokenRepository securityTokenRepository;

    private CookieToHeadersFilter cookieToHeadersFilter;

    private AuthenticationManager authenticationManager;

    private LogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //添加请求头
        http.addFilterBefore(cookieToHeadersFilter,SecurityWebFiltersOrder.HTTP_HEADERS_WRITER);

        //权限控制
        Authentication(http);


        //登录逻辑控制
        return http
                .formLogin().loginPage("/auth/login")
                .authenticationSuccessHandler(authenticationSuccess)
                .authenticationFailureHandler(authenticationFailed)
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntrypoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and().authenticationManager(reactiveAuthenticationManager())
                .securityContextRepository(securityTokenRepository)

                .httpBasic().disable()
                .csrf().disable()
                .logout().logoutUrl("/auth/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .build();
    }

    public void Authentication(ServerHttpSecurity http) {
        http.authorizeExchange(authorize -> {
            authorize.pathMatchers("/auth/login", "/auth/signup").permitAll()
                    .pathMatchers("/v3/api-docs/**", "/swagger-resources/configuration/ui",
                            "/swagger-resources", "/swagger-resources/configuration/security",
                            "/swagger-ui.html", "/css/**", "/js/**", "/images/**", "/webjars/**", "**/favicon.ico", "/index").permitAll();

            getMap().forEach((k,v)->authorize.pathMatchers(k).hasAuthority(v));

            authorize.anyExchange().denyAll();
        });
    }

    public Map<String, String> getMap(){
        Map<String, String> map = new HashMap<>();
        return map;
    }


    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
        managers.add(authentication -> {
            // 其他登陆方式
            return Mono.empty();
        });
        managers.add(authenticationManager);
        return new DelegatingReactiveAuthenticationManager(managers);
    }



}
