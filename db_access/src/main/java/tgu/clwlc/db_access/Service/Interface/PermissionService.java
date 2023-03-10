package tgu.clwlc.db_access.Service.Interface;

import tgu.clwlc.FeignClient.pojo.mysql.Permission.Permission;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.UserP;

import java.util.List;

public interface PermissionService {
    List<Permission> getPermissionList();

    UserP getUserP(long pid);

    boolean addUserP_employee(UserP userP);

    boolean addUserP_admin(UserP userP);

    List<Permission> getPermissionByRoleName(String role);
}
