package tgu.clwlc.db_access.Controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tgu.clwlc.db_access.dao.ShopMapper;
import tgu.clwlc.db_access.pojo.mysql.Shop;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    ShopMapper shopMapper;

    @PostMapping
    public int addShop(Shop shop){
        shopMapper.insert(shop);
        return 0;
    }

}
