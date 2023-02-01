package tgu.clwlc.account.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tgu.clwlc.account.entity.User;

@Mapper
public interface AccountDao extends BaseMapper<User> {
}
