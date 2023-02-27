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
public class tokenUser {
    private String username;
    private List<String> permission;

    public tokenUser(String username, List<String> permission) {
        this.username = username;
        this.permission = permission;
    }

    public Collection<GrantedAuthority> permission(){
        Collection<GrantedAuthority> collection = new ArrayList<>();
        permission.forEach(p->collection.add(new SimpleGrantedAuthority(p)));
        return collection;
    }
    public void addPermission(String permission){
        this.permission.add(permission);
    }

}
