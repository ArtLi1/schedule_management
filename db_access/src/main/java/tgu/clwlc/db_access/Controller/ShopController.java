package tgu.clwlc.db_access.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tgu.clwlc.db_access.dao.ShopMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Resource
    ShopMapper shopMapper;


}
