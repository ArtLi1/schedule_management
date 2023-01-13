package re;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tgu.clwlc.FeignClient.API.dbAccessApi;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;
import tgu.clwlc.account.accountApplication;

import tgu.clwlc.account.util.SnowflakeIdGenerate;

@SpringBootTest(classes = accountApplication.class)
public class test {

    @Autowired
    dbAccessApi dbAccessApi;

    @Test
    public void t1(){
        SnowflakeIdGenerate snowflakeIdGenerate = new SnowflakeIdGenerate(1);
        Shop shop1 = new Shop(snowflakeIdGenerate.nextId(),"t1","ad1",100);
        Shop shop2 = new Shop(snowflakeIdGenerate.nextId(),"t2","ad3",100);
        dbAccessApi.addShop(shop1);
        dbAccessApi.addShop(shop2);

    }
}
