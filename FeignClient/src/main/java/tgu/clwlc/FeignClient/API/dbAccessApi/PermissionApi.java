package tgu.clwlc.FeignClient.API.dbAccessApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.Permission;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.UserP;

import java.util.List;

@FeignClient(name = "dbAccess",contextId = "permission")
public interface PermissionApi {

    @GetMapping("/permission")
    List<Permission> getPermission();

    @GetMapping("/permission/pid/{pid}")
    UserP getUserP(@PathVariable long pid);

    @GetMapping("/permission/role/{role}")
    List<Permission> getPermissionByRoleName(@PathVariable String role);

    @PostMapping("/permission/employee")
    boolean addUserP_employee(@RequestBody UserP userP);

    @PostMapping("/permission/admin")
    boolean addUserP_admin(@RequestBody UserP userP);
}
