package tgu.Gateway.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class tokenUser {
    private String username;
    private int permission;

    public Collection<GrantedAuthority> permissionList(){
        List<GrantedAuthority> list = new ArrayList<>();
        list.add( new  SimpleGrantedAuthority(permission+""));
        return list;
    }
}
