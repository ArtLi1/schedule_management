package tgu.Gateway.security.Handler;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tgu.Gateway.pojo.SecurityUser;
import tgu.Gateway.security.conf.PassWordEncoder;
import tgu.Gateway.security.conf.UserDetailsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private UserDetailsService userDetailsService;

    private PassWordEncoder encoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if(authentication.isAuthenticated()) return Mono.just(authentication);
        UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());
        return Mono.just(user)
                .filter(s->encoder.matches((String) authentication.getCredentials(), s.getPassword()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("Invalid Credentials"))))
                .map(s -> {
                    if(s.getUsername().equals("")){
                        return authentication;
                    }
                    return new UsernamePasswordAuthenticationToken(s.getUsername(), s.getPassword(), s.getAuthorities());
                });
    }
}
