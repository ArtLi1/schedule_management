package tgu.clwlc.db_access.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tgu.clwlc.FeignClient.pojo.mysql.UserWithP;

@Component
public interface UserPMapper extends BaseMapper<UserWithP> {

    UserWithP selectByUid(@Param("uid")Long uid);

    UserWithP selectByEmail(@Param("email")String email);

    UserWithP selectByPhone(@Param("phone")String phone);

}
