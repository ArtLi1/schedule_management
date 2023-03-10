package tgu.clwlc.db_access.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.Permission;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.Role;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.UserP;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.RoleP;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectAll();

    UserP selectUserPByPid(@Param("pid")long pid);

    RoleP selectRolePByPid(@Param("pid")long pid);

    Role selectRoleByRName(@Param("rName")String rName);

    void insertUserP(@Param("uid")long uid,@Param("pid")long pid);

    List<Permission> selectPermissionByRoleName(@Param("rName")String rName);

}
