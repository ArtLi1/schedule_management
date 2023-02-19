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
import tgu.Gateway.security.Filters.AuthenticationFailed;
import tgu.Gateway.security.Filters.AuthenticationSuccess;
import tgu.Gateway.security.Filters.CookieToHeadersFilter;
import tgu.Gateway.security.Filters.SecurityTokenRepository;
import tgu.Gateway.security.Handler.AccessDeniedHandler;
import tgu.Gateway.security.Handler.AuthenticationEntrypoint;
import tgu.Gateway.security.Handler.AuthenticationManager;

import java.util.LinkedList;

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

    private static final String[] s1 = {"/schedule/**","/t1/**"};
    private static final String[] s2 = {"/t2/**"};
    private static final String[] s3 = {"/t3/**"};

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //添加请求头
        http.addFilterBefore(cookieToHeadersFilter,SecurityWebFiltersOrder.HTTP_HEADERS_WRITER);

        //权限控制
        http.authorizeExchange()
                .pathMatchers("/auth/login", "/auth/signup").permitAll()
                .pathMatchers("/v3/api-docs/**", "/swagger-resources/configuration/ui",
                        "/swagger-resources", "/swagger-resources/configuration/security",
                        "/swagger-ui.html", "/css/**", "/js/**", "/images/**", "/webjars/**", "**/favicon.ico", "/index").permitAll()

                .pathMatchers("/schedule/**","/t1/**").hasAnyAuthority("1")

                .pathMatchers("/t2/**").hasAnyAuthority("2")

                .pathMatchers("/t3/**").hasAnyAuthority("3")
                .anyExchange().authenticated();

        //登录逻辑控制
        return http
                .formLogin().loginPage("/auth/login")
                .authenticationSuccessHandler(authenticationSuccess)
                .authenticationFailureHandler(authenticationFailed)
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntrypoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and().authenticationManager(reactiveAuthenticationManager())
                .httpBasic().disable()
                .csrf().disable()
                .securityContextRepository(securityTokenRepository)
                .build();
    }

    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
        managers.add(authentication -> {
            // 其他登陆方式
            return Mono.empty();
        });
        managers.add(authenticationManager);
//        managers.add(new UserDetailsRepositoryReactiveAuthenticationManager(null));
        return new DelegatingReactiveAuthenticationManager(managers);
    }



}
