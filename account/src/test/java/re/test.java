package re;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgu.clwlc.FeignClient.API.dbAccessApi;
import tgu.clwlc.account.accountApplication;

@SpringBootTest(classes = accountApplication.class)
public class test {

    @Autowired
    dbAccessApi dbAccessApi;

    @Test
    public void t1(){

    }
}
