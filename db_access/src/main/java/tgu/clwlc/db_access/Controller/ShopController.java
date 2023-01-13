package tgu.clwlc.db_access.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;
import tgu.clwlc.db_access.dao.ShopMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    ShopMapper shopMapper;

    @PostMapping
    public int addShop(@RequestBody Shop shop){
        System.out.println(shop.toString());
        shopMapper.insert(shop);
        return 0;
    }

}
