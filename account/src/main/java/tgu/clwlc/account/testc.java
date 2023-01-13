package tgu.clwlc.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tgu.clwlc.account.util.SnowflakeIdGenerate;

@RestController
public class testc {

//    @Autowired
//    dbAccessApi dbAccessApi;

    SnowflakeIdGenerate snowflakeIdGenerate = new SnowflakeIdGenerate(1);

    @GetMapping("/t1")
    public int t1(){
//        Shop shop1 = new Shop(snowflakeIdGenerate.nextId(),"t1","ad1",100);
//        Shop shop2 = new Shop(snowflakeIdGenerate.nextId(),"t2","ad3",100);
//        dbAccessApi.addShop(shop1);
//        dbAccessApi.addShop(shop2);
        return 1;
    }
}
