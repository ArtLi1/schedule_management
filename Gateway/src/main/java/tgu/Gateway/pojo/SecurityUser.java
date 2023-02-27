package tgu.Gateway.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tgu.clwlc.FeignClient.pojo.mysql.UserWithP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final String username;

    private final String password;

    private final List<String> permission;

    public SecurityUser(UserWithP user){
        username = user.getEmail();
        password = user.getPassword();
        permission = user.getPermission();
    }

    public SecurityUser(String username, String password, List<String> permission) {
        this.username = username;
        this.password = password;
        this.permission = permission;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        permission.forEach(p->list.add(new SimpleGrantedAuthority(p)));
        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
