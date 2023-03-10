package tgu.clwlc.db_access.Service.Impl;

import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.Permission;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.UserP;
import tgu.clwlc.db_access.Service.Interface.PermissionService;
import tgu.clwlc.db_access.dao.PermissionMapper;

import javax.annotation.Resource;
import java.util.List;

import static tgu.clwlc.db_access.Utils.PermissionUtils.ADMIN;
import static tgu.clwlc.db_access.Utils.PermissionUtils.EMPLOYEE;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionList() {
        return permissionMapper.selectAll();
    }

    @Override
    public UserP getUserP(long pid) {
        return permissionMapper.selectUserPByPid(pid);
    }


    @Override
    public boolean addUserP_employee(UserP userP) {
        long rid = permissionMapper.selectRolePByPid(userP.getPid()).getRid();
        if (rid != permissionMapper.selectRoleByRName(EMPLOYEE).getRid()) {
            return false;
        }
        permissionMapper.insertUserP(userP.getUid(), userP.getPid());
        return true;
    }

    @Override
    public boolean addUserP_admin(UserP userP) {
        long rid = permissionMapper.selectRolePByPid(userP.getPid()).getRid();
        if (rid != permissionMapper.selectRoleByRName(ADMIN).getRid()) {
            return false;
        }
        permissionMapper.insertUserP(userP.getUid(), userP.getPid());
        return true;
    }

    @Override
    public List<Permission> getPermissionByRoleName(String role) {
        return permissionMapper.selectPermissionByRoleName(role);
    }
}
