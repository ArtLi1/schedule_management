package tgu.clwlc.db_access.Service.Impl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import tgu.clwlc.FeignClient.pojo.mysql.User;
import tgu.clwlc.FeignClient.util.SnowflakeIdGenerate;
import tgu.clwlc.db_access.Service.Interface.UserService;
import tgu.clwlc.db_access.dao.UserMapper;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    SnowflakeIdGenerate idGenerate = new SnowflakeIdGenerate(2);

    @Override
    public User getUser(long uid) {
        return userMapper.selectById(uid);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getEmail,email));
    }

    @Override
    public User getUserByPhone(long phone) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getPhone,phone));
    }

    @Override
    public List<User> getUserList(long sid) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("sid",sid);
        return userMapper.selectList(query);
    }

    @Override
    public boolean addUser(User user) {
        if(getUserByEmail(user.getEmail())!=null||getUserByPhone(user.getPhone())!=null){
            return false;
        }
        user.setId(idGenerate.nextId());
        return userMapper.insert(user)>0;
    }

    @Override
    public boolean modifyUser(User user) {
        return userMapper.updateById(user)>0;
    }

    @Override
    public boolean removeUser(long uid) {
        return userMapper.deleteById(uid)>0;
    }
}
