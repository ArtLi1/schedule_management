package tgu.clwlc.db_access.Controller;

import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.Permission;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.UserP;
import tgu.clwlc.db_access.Service.Interface.PermissionService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    PermissionService permissionService;

    @GetMapping
    public List<Permission> getPermission(){
        return permissionService.getPermissionList();
    }

    @GetMapping("/pid/{pid}")
    public UserP getUserP(@PathVariable long pid) {
        return permissionService.getUserP(pid);
    }

    @GetMapping("/role/{role}")
    public List<Permission> getPermissionByRoleName(@PathVariable String role){
        return permissionService.getPermissionByRoleName(role);
    }

    @PostMapping("/employee")
    public boolean addUserP_employee(@RequestBody UserP userP) {
        return permissionService.addUserP_employee(userP);
    }

    @PostMapping("/admin")
    public boolean addUserP_admin(@RequestBody UserP userP) {
        return permissionService.addUserP_admin(userP);
    }

}
