package tgu.Gateway.Utils;

import org.springframework.stereotype.Component;
import tgu.clwlc.FeignClient.API.dbAccessApi.PermissionApi;
import tgu.clwlc.FeignClient.pojo.mysql.Permission.Permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PermissionUtils {

    PermissionApi permissionApi;

    public PermissionUtils(PermissionApi permissionApi) {
        this.permissionApi = permissionApi;
        List<Permission> permission = permissionApi.getPermission();
        permissionList = new HashMap<>();
        permission.forEach(p-> permissionList.put(p.getUrl(),p.getPName()));
    }

    private static Map<String,String> permissionList;

    public Map<String, String> getPermissionList() {
        if(permissionList!=null) return permissionList;
        List<Permission> permission = permissionApi.getPermission();
        permissionList = new HashMap<>();
        permission.forEach(p-> permissionList.put(p.getUrl(),p.getPName()));
        return permissionList;
    }

}
