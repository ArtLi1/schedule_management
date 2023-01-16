package tgu.clwlc.db_access.Controller;

import org.springframework.web.bind.annotation.*;
import tgu.clwlc.FeignClient.pojo.mysql.Shop;
import tgu.clwlc.FeignClient.util.SnowflakeIdGenerate;
import tgu.clwlc.db_access.dao.ShopMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    ShopMapper shopMapper;

    SnowflakeIdGenerate idGenerate = new SnowflakeIdGenerate(1);

    @GetMapping("/sid/{sid}")
    public Shop getShop(@PathVariable long sid){
        return shopMapper.selectById(sid);
    }

    @PostMapping
    public int addShop(@RequestBody Shop shop){
        shop.setId(idGenerate.nextId());
        shopMapper.insert(shop);
        return 0;
    }

}
