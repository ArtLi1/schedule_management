package re;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tgu.clwlc.db_access.dao.UserPMapper;
import tgu.clwlc.db_access.dbAccessApplication;

import javax.annotation.Resource;

@SpringBootTest(classes = dbAccessApplication.class)
public class t1 {

    @Resource
    UserPMapper userPMapper;

    @Test
    public void t1() throws IllegalAccessException {
        System.out.println(userPMapper.selectByUid(374978540699649L));
    }

}
